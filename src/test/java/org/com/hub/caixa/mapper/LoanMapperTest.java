package org.com.hub.caixa.mapper;

import com.hub.caixa.model.LoanDTO;
import org.com.hub.caixa.model.Loan;
import org.com.hub.caixa.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LoanMapperTest {

    private LoanMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(LoanMapper.class);
    }

    @ParameterizedTest
    @MethodSource("loanWithCreatedBy")
    void toDto_shouldMapCreatedByCorrectly(Loan loan, UUID expectedCreatedById) {
        // when
        LoanDTO dto = mapper.toDto(loan);

        // then
        assertNotNull(dto);
        assertEquals(expectedCreatedById, dto.getCreatedBy());
    }

    static Stream<Arguments> loanWithCreatedBy() {
        UUID userId = UUID.randomUUID();

        Loan loanWithCreatedBy = Loan.builder().createdBy(buildUser(userId)).build();

        Loan loanWithoutCreatedBy = Loan.builder().createdBy(null).build();

        return Stream.of(
                Arguments.of(loanWithCreatedBy, userId),
                Arguments.of(loanWithoutCreatedBy, null)
        );
    }

    @Test
    void toEntity_shouldIgnoreCreatedBy() {
        // given
        LoanDTO dto = new LoanDTO();
        dto.setCreatedBy(UUID.randomUUID());

        // when
        Loan loan = mapper.toEntity(dto);

        // then
        assertNotNull(loan);
        assertNull(loan.getCreatedBy());
    }

    private static User buildUser(UUID id) {
        return User.builder().id(id).build();
    }
}

