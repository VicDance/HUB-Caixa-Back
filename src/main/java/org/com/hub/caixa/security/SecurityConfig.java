package org.com.hub.caixa.security;

import com.hub.caixa.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.com.hub.caixa.model.Loan;
import org.com.hub.caixa.repository.LoanRepository;
import org.com.hub.caixa.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers ->
                        headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("manager")
                .password(encoder.encode("manager"))
                .roles("MANAGER")
                .build());

        manager.createUser(User.withUsername("client")
                .password(encoder.encode("client"))
                .roles("CLIENT")
                .build());

        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public boolean isOwnerOrManager(UUID loanId, UUID userId) {
        Optional<Loan> loanOpt = loanRepository.findById(loanId);
        if (loanOpt.isEmpty()) return false;

        Loan loan = loanOpt.get();
        org.com.hub.caixa.model.User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean isManager = currentUser.getRole().name().equals(UserDTO.RoleEnum.MANAGER.getValue());
        boolean isOwner = loan.getCreatedBy().getId().equals(userId);

        return isManager || isOwner;
    }
}
