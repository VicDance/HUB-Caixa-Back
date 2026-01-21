package org.com.hub.caixa.service;

import com.hub.caixa.model.LoanDTO;
import com.hub.caixa.model.UpdateLoanStatusRequest;
import org.com.hub.caixa.mapper.LoanMapper;
import org.com.hub.caixa.model.Loan;
import org.com.hub.caixa.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LoanService {
    private final LoanRepository loanRepository;
    private final LoanMapper loanMapper;

    public LoanService(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    public LoanDTO createLoan(LoanDTO dto) {
        Loan loan = loanMapper.toEntity(dto);
        loan.setCreatedAt(LocalDateTime.now());
        loan.setStatus("PENDING");
        Loan saved = loanRepository.save(loan);
        return loanMapper.toDto(saved);
    }

    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll().stream()
                .map(loanMapper::toDto)
                .collect(Collectors.toList());
    }

    public Optional<LoanDTO> getLoan(UUID id) {
        return loanRepository.findById(id)
                .map(loanMapper::toDto);
    }

    public Optional<LoanDTO> updateStatus(UUID id, UpdateLoanStatusRequest newStatus) {
        Optional<Loan> optionalLoan = loanRepository.findById(id);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            loan.setStatus(newStatus.getStatus().getValue());
            return Optional.of(loanMapper.toDto(loanRepository.save(loan)));
        }
        return Optional.empty();
    }
}
