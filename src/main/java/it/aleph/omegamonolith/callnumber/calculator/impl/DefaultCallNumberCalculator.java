package it.aleph.omegamonolith.callnumber.calculator.impl;

import it.aleph.omegamonolith.callnumber.calculator.CallNumberCalculator;
import it.aleph.omegamonolith.callnumber.calculator.CallNumberResult;
import it.aleph.omegamonolith.cutter.model.CutterNumberFact;
import it.aleph.omegamonolith.dao.catalog.BookRepository;
import it.aleph.omegamonolith.dto.catalog.book.BookDto;
import it.aleph.omegamonolith.exception.CutterProcessingException;
import it.aleph.omegamonolith.mapper.catalog.BookCutterFactMapper;
import it.aleph.omegamonolith.model.catalog.Book;
import lombok.RequiredArgsConstructor;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.FactHandle;
import org.springframework.stereotype.Component;
import it.aleph.omegamonolith.callnumber.table.*;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class DefaultCallNumberCalculator implements CallNumberCalculator {

    private final BookCutterFactMapper mapper;
    private final KieContainer kieContainer;
    private final CallNumberTable<CallNumberTable<String>> callNumberTable;
    private final BookRepository bookRepository;

    @Override
    public CallNumberResult calculate(BookDto bookDto) {
        CutterNumberFact fact = mapper.toFact(bookDto);
        CallNumberResult result = new CallNumberResult();
        KieSession kieSession = kieContainer.newKieSession();
        String accessPointLetters = fact.getAccessPoint().substring(0,2).toLowerCase();
        for(String keyRegex : callNumberTable.keySet()){
            if(accessPointLetters.startsWith(keyRegex)){
                fact.setCutterNumberMapping(callNumberTable.get(keyRegex));
            }
        }
        fact.setCutterNumberExpansionMapping(callNumberTable.get("expansion"));


        FactHandle factHandle = kieSession.insert(fact);
        kieSession.fireAllRules();

        Book bookWithCutterNumber = bookRepository.findBookWithCutterNumber(fact.getCutterNumber(), fact.getIsbn(), fact.getAccessPoint());
        fact.setBookWithSameCutterNumber(bookWithCutterNumber);

        kieSession.update(factHandle, fact);
        kieSession.fireAllRules();
        kieSession.dispose();

        if(Objects.nonNull(fact.getCutterError())){
            String message = fact.getCutterError().getMessage();
            String cause = fact.getCutterError().getCause();
            throw CutterProcessingException.builder().message(message).messageCauseList(List.of(cause)).build();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String callNumber = bookDto.getDeweyDecimalCode() + fact.getCutterNumber() + formatter.format(bookDto.getPubDate());
        result.setCutterNumber(fact.getCutterNumber());
        result.setCallNumber(callNumber);
        return result;

    }
}
