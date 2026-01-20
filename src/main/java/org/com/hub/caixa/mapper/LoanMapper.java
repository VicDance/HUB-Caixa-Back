package org.com.hub.caixa.mapper;

import com.hub.caixa.model.LoanDTO;
import org.com.hub.caixa.model.Loan;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    LoanDTO toDto(Loan loan);

    Loan toEntity(LoanDTO dto);
}
