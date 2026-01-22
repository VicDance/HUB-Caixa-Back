package org.com.hub.caixa.controller;

import com.hub.caixa.model.LoanDTO;
import com.hub.caixa.model.UpdateLoanStatusRequest;
import org.com.hub.caixa.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanControllerImplTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanControllerImpl controller;

    private UUID loanId;
    private UUID userId;
    private LoanDTO loanDTO;

    @BeforeEach
    void setUp() {
        loanId = UUID.randomUUID();
        userId = UUID.randomUUID();
        loanDTO = new LoanDTO();
    }

    @Test
    void createLoan_shouldReturnOk() {
        // when
        when(loanService.createLoan(loanDTO, userId)).thenReturn(loanDTO);

        ResponseEntity<LoanDTO> response = controller.createLoan(userId, loanDTO);

        // then
        assertEquals(200, response.getStatusCode().value());
        assertEquals(loanDTO, response.getBody());
        verify(loanService).createLoan(loanDTO, userId);
    }

    @Test
    void getLoanById_shouldReturnOkIfExists() {
        when(loanService.getLoan(loanId)).thenReturn(Optional.of(loanDTO));

        ResponseEntity<LoanDTO> response = controller.getLoanById(loanId, userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(loanDTO, response.getBody());
    }

    @Test
    void getLoanById_shouldReturnNotFoundIfMissing() {
        when(loanService.getLoan(loanId)).thenReturn(Optional.empty());

        ResponseEntity<LoanDTO> response = controller.getLoanById(loanId, userId);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void getLoans_shouldReturnList() {
        List<LoanDTO> list = List.of(loanDTO);
        when(loanService.getAllLoans()).thenReturn(list);

        ResponseEntity<List<LoanDTO>> response = controller.getLoans();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }

    @Test
    void updateLoanStatus_shouldReturnOkIfExists() {
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        when(loanService.updateStatus(loanId, request)).thenReturn(Optional.of(loanDTO));

        ResponseEntity<LoanDTO> response = controller.updateLoanStatus(loanId, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(loanDTO, response.getBody());
    }

    @Test
    void updateLoanStatus_shouldReturnNotFoundIfMissing() {
        UpdateLoanStatusRequest request = new UpdateLoanStatusRequest();
        when(loanService.updateStatus(loanId, request)).thenReturn(Optional.empty());

        ResponseEntity<LoanDTO> response = controller.updateLoanStatus(loanId, request);

        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }
}