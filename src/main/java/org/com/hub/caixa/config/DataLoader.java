package org.com.hub.caixa.config;

import com.hub.caixa.model.LoanDTO;
import com.hub.caixa.model.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.com.hub.caixa.mapper.LoanMapper;
import org.com.hub.caixa.mapper.UserMapper;
import org.com.hub.caixa.model.Loan;
import org.com.hub.caixa.model.User;
import org.com.hub.caixa.service.LoanService;
import org.com.hub.caixa.service.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataLoader {
    private final UserService userService;
    private final LoanService loanService;
    private final UserMapper userMapper;
    private final LoanMapper loanMapper;

    @PostConstruct
    public void loadData() {
        User manager = userMapper.toEntity(
                userService.createUser(
                        createUser("manager1", User.RoleEnum.MANAGER)
                )
        );
        User client1 = userMapper.toEntity(
                userService.createUser(
                        createUser("client1", User.RoleEnum.CLIENT)
                )
        );
        User client2 = userMapper.toEntity(
                userService.createUser(
                        createUser("client2", User.RoleEnum.CLIENT)
                )
        );

        loanMapper.toEntity(
                loanService.createLoan(
                        createLoan("Alice", 1000, "EUR", "12345678A", client1),
                        client1.getId()
                )
        );
        loanMapper.toEntity(
                loanService.createLoan(
                        createLoan("Bob", 1500, "USD", "87654321B", client2),
                        client2.getId()
                )
        );
        loanMapper.toEntity(
                loanService.createLoan(
                        createLoan("Bob", 3000, "USD", "87654321B", client2),
                        client2.getId()
                )
        );

        loanMapper.toEntity(
                loanService.createLoan(
                        createLoan("Manager Loan", 5000, "EUR", "99999999C", manager),
                        manager.getId()
                )
        );
    }

    private UserDTO createUser(String username, User.RoleEnum role) {
        return userMapper.toDto(User.builder().username(username).role(role).build());
    }

    private LoanDTO createLoan(String username, Integer amount, String currency, String documentId, User user) {
        return loanMapper.toDto(
                Loan.builder()
                        .applicantName(username)
                        .amount(amount)
                        .currency(currency)
                        .documentId(documentId)
                        .createdBy(user)
                        .build()
        );
    }
}
