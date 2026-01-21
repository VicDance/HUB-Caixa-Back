package org.com.hub.caixa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loans")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(nullable = false)
    private String applicantName;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private String currency;
    @Column(nullable = false)
    private String documentId;

    private LocalDateTime createdAt;
    private String status;
}
