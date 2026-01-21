package org.com.hub.caixa.controller;

import com.hub.caixa.api.LoansApi;
import com.hub.caixa.model.LoanDTO;
import com.hub.caixa.model.UpdateLoanStatusRequest;
import org.com.hub.caixa.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class LoanControllerImpl implements LoansApi {
    private final LoanService loanService;

    public LoanControllerImpl(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public ResponseEntity<LoanDTO> createLoan(LoanDTO loanDTO) {
        LoanDTO created = loanService.createLoan(loanDTO);
        return ResponseEntity.ok(created);
    }

    @Override
    public ResponseEntity<LoanDTO> getLoanById(UUID id) {
        Optional<LoanDTO> optional = loanService.getLoan(id);
        return optional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<List<LoanDTO>> getLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }

    @Override
    public ResponseEntity<LoanDTO> updateLoanStatus(UUID id, UpdateLoanStatusRequest newStatus) {
        Optional<LoanDTO> optional = loanService.updateStatus(id, newStatus);
        return optional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
