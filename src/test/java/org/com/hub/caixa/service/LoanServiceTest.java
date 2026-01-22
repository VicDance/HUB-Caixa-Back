package org.com.hub.caixa.service;

import com.hub.caixa.model.LoanDTO;
import com.hub.caixa.model.UpdateLoanStatusRequest;
import org.com.hub.caixa.mapper.LoanMapper;
import org.com.hub.caixa.model.Loan;
import com.hub.caixa.model.UpdateLoanStatusRequest.StatusEnum;
import org.com.hub.caixa.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private LoanMapper loanMapper;

    @InjectMocks
    private LoanService loanService;

    @Test
    void createLoan_shouldCreateLoanWithPendingStatus() {
        // given
        UUID userId = UUID.randomUUID();
        LoanDTO dto = new LoanDTO();

        Loan loan = new Loan();
        Loan savedLoan = new Loan();
        savedLoan.setStatus("PENDING");

        // when
        when(loanMapper.toEntity(dto)).thenReturn(loan);
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);
        when(loanMapper.toDto(savedLoan)).thenReturn(dto);

        LoanDTO result = loanService.createLoan(dto, userId);

        // then
        assertNotNull(result);

        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());

        Loan capturedLoan = loanCaptor.getValue();
        assertEquals("PENDING", capturedLoan.getStatus());
        assertNotNull(capturedLoan.getCreatedAt());
        assertEquals(userId, capturedLoan.getCreatedBy().getId());
    }

    @Test
    void getAllLoans_shouldReturnListOfDtos() {
        // given
        Loan loan1 = Loan.builder().build();
        Loan loan2 = Loan.builder().build();
        LoanDTO dto1 = new LoanDTO();
        LoanDTO dto2 = new LoanDTO();

        // when
        when(loanRepository.findAll()).thenReturn(List.of(loan1, loan2));
        when(loanMapper.toDto(loan1)).thenReturn(dto1);
        when(loanMapper.toDto(loan2)).thenReturn(dto2);

        List<LoanDTO> result = loanService.getAllLoans();

        // then
        assertEquals(2, result.size());
        verify(loanRepository).findAll();
        verify(loanMapper, times(2)).toDto(any());
    }

    @Test
    void getLoan_shouldReturnDtoIfExists() {
        // given
        UUID loanId = UUID.randomUUID();
        Loan loan = Loan.builder().build();
        LoanDTO dto = new LoanDTO();

        // when
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanMapper.toDto(loan)).thenReturn(dto);

        Optional<LoanDTO> result = loanService.getLoan(loanId);

        // then
        assertTrue(result.isPresent());
        assertEquals(dto, result.get());
    }

    @Test
    void getLoan_shouldReturnEmptyIfNotExists() {
        // given
        UUID loanId = UUID.randomUUID();

        // when
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        Optional<LoanDTO> result = loanService.getLoan(loanId);

        // then
        assertTrue(result.isEmpty());
        verify(loanMapper, never()).toDto(any());
    }

    @Test
    void updateStatus_shouldUpdateAndReturnDtoIfLoanExists() {
        // given
        UUID loanId = UUID.randomUUID();
        Loan loan = Loan.builder().build();
        LoanDTO dto = new LoanDTO();

        UpdateLoanStatusRequest request = mock(UpdateLoanStatusRequest.class);
        StatusEnum status = mock(StatusEnum.class);

        // when
        when(status.getValue()).thenReturn("APPROVED");
        when(request.getStatus()).thenReturn(status);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));
        when(loanRepository.save(loan)).thenReturn(loan);
        when(loanMapper.toDto(loan)).thenReturn(dto);

        Optional<LoanDTO> result = loanService.updateStatus(loanId, request);

        // then
        assertTrue(result.isPresent());
        assertEquals("APPROVED", loan.getStatus());
    }

    @Test
    void updateStatus_shouldReturnEmptyIfLoanNotExists() {
        // given
        UUID loanId = UUID.randomUUID();
        UpdateLoanStatusRequest request = mock(UpdateLoanStatusRequest.class);

        // when
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        Optional<LoanDTO> result = loanService.updateStatus(loanId, request);

        // then
        assertTrue(result.isEmpty());
        verify(loanRepository, never()).save(any());
    }
}

