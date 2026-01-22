package org.com.hub.caixa.mapper;

import com.hub.caixa.model.UserDTO;
import org.com.hub.caixa.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDto(User dto);

    User toEntity(UserDTO dto);
}
