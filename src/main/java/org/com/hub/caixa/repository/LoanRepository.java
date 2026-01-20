package org.com.hub.caixa.repository;

import org.com.hub.caixa.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<Loan, UUID> {
}
