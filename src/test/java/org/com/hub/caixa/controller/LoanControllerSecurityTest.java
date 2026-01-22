package org.com.hub.caixa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hub.caixa.model.LoanDTO;
import org.com.hub.caixa.service.LoanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LoanControllerSecurityTest {
    @MockBean
    private LoanService loanService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "CLIENT")
    void createLoan_allowedForClient() throws Exception {
        // given
        UUID userId = UUID.randomUUID();
        LoanDTO dto = new LoanDTO();
        dto.setApplicantName("Jane Doe");
        dto.setAmount(2000.0);
        dto.setCurrency("EUR");
        dto.setDocumentId("12233445A");

        // when
        when(loanService.createLoan(any(), any())).thenReturn(dto);

        // then
        mockMvc.perform(post("/loans")
                        .header("X-User-Id", userId)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "MANAGER")
    void getLoans_allowedForManager() throws Exception {
        // when
        when(loanService.getAllLoans()).thenReturn(List.of());

        // then
        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENT")
    void getLoans_forbiddenForClient() throws Exception {
        mockMvc.perform(get("/loans"))
                .andExpect(status().isForbidden());
    }
}
