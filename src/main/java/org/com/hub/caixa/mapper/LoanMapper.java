package org.com.hub.caixa.mapper;

import com.hub.caixa.model.LoanDTO;
import org.com.hub.caixa.model.Loan;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LoanMapper {
    @Mapping(target = "createdBy", expression = "java(loan.getCreatedBy() != null ? loan.getCreatedBy().getId() : null)")
    LoanDTO toDto(Loan loan);

    @Mapping(target = "createdBy", ignore = true)
    Loan toEntity(LoanDTO dto);
}
