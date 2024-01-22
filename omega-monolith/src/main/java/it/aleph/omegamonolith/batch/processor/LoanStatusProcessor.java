package it.aleph.omegamonolith.batch.processor;

import it.aleph.omegamonolith.config.loan.state.machine.LoanStatusEvent;
import it.aleph.omegamonolith.model.loan.Loan;
import it.aleph.omegamonolith.model.loan.LoanStatus;
import jakarta.annotation.Nonnull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.HashMap;

@Component
public class LoanStatusProcessor implements ItemProcessor<Loan, Loan> {


    @Override
    public Loan process(@Nonnull Loan item) throws Exception {
        StateMachineBuilder.Builder<LoanStatus, LoanStatusEvent> stateMachineBuilder = StateMachineBuilder.builder();
        stateMachineBuilder.configureStates()
                .withStates()
                .state(LoanStatus.ISSUED)
                .state(LoanStatus.ACCEPTED)
                .state(LoanStatus.EXPIRED)
                .initial(item.getLoanStatus());

        stateMachineBuilder.configureTransitions().withExternal().source(LoanStatus.ISSUED)
                .target(LoanStatus.ACCEPTED)
                .event(LoanStatusEvent.ADVANCE)
                .and()
                .withExternal()
                .source(LoanStatus.ACCEPTED)
                .target(LoanStatus.EXPIRED)
                .event(LoanStatusEvent.ADVANCE)
                .guard((s) -> item.getEndDate().before(new Date()));

        StateMachine<LoanStatus, LoanStatusEvent> stateMachine = stateMachineBuilder.build();
        stateMachine.startReactively().subscribe();
        stateMachine.sendEvent(Mono.just(MessageBuilder.createMessage(LoanStatusEvent.ADVANCE, new MessageHeaders(new HashMap<>())))).subscribe();
        item.setLoanStatus(stateMachine.getState().getId());
        return item;
    }
}
