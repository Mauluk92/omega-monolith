package it.aleph.omegamonolith.controller.loan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.aleph.omegamonolith.controller.advice.OmegaControllerAdvice;
import it.aleph.omegamonolith.controller.loan.impl.LoanControllerImpl;
import it.aleph.omegamonolith.dto.loan.LoanDto;
import it.aleph.omegamonolith.dto.loan.LoanStatusDto;
import it.aleph.omegamonolith.exception.NotFoundException;
import it.aleph.omegamonolith.providers.controller.loan.GetLoanControllerProvider;
import it.aleph.omegamonolith.providers.controller.loan.InvalidIssueLoanControllerProvider;
import it.aleph.omegamonolith.providers.controller.loan.IssueLoanControllerProvider;
import it.aleph.omegamonolith.providers.controller.loan.UpdateLoanStatusControllerProvider;
import it.aleph.omegamonolith.service.loan.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes =
        {LoanControllerImpl.class,
                ObjectMapper.class,
                OmegaControllerAdvice.class}
)
public class LoanControllerTest {

    @MockBean
    private LoanService loanService;
    @Autowired
    private LoanController loanController;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private OmegaControllerAdvice omegaControllerAdvice;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        objectMapper.registerModule(new JavaTimeModule());
        mockMvc = MockMvcBuilders
                .standaloneSetup(loanController)
                .setControllerAdvice(omegaControllerAdvice)
                .build();
    }
    @ParameterizedTest
    @ArgumentsSource(GetLoanControllerProvider.class)
    public void getLoanByIdTest(long id, LoanDto loanDto) throws Exception {
        Mockito.when(loanService.getLoanById(id)).thenReturn(loanDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(loanDto)))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getNonExistentLoan() throws Exception {
        NotFoundException notFoundException = NotFoundException.builder().idListNotFound(List.of(1L)).message("Not found loan").build();
        Mockito.when(loanService.getLoanById(1L)).thenThrow(notFoundException);
        mockMvc.perform(MockMvcRequestBuilders.get("/loan/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @ParameterizedTest
    @ArgumentsSource(IssueLoanControllerProvider.class)
    public void createLoanTest(LoanDto loanDto, LoanDto createdLoanDto) throws Exception {
        Mockito.when(loanService.issueLoan(Mockito.any(LoanDto.class))).thenReturn(createdLoanDto);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/loan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loanDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(createdLoanDto)));
    }

    @ParameterizedTest
    @ArgumentsSource(UpdateLoanStatusControllerProvider.class)
    public void updateLoanDto(long id, LoanStatusDto loanStatusDto, LoanDto loanDto) throws Exception {
        Mockito.when(loanService.updateLoanStatus(id, loanStatusDto)).thenReturn(loanDto);
        mockMvc.perform(MockMvcRequestBuilders.patch("/loan/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loanStatusDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(loanDto)));
    }

    @Test
    public void removeLoanById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/loan/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @ParameterizedTest
    @ArgumentsSource(InvalidIssueLoanControllerProvider.class)
    public void invalidIssuedLoan(LoanDto invalidLoan) throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                .post("/loan")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidLoan)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


}
