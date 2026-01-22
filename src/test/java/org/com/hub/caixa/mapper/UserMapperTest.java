package org.com.hub.caixa.mapper;

import com.hub.caixa.model.UserDTO;
import org.com.hub.caixa.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UserMapper.class);
    }

    @ParameterizedTest
    @MethodSource("user")
    void toDto_shouldMapUserCorrectly(User user) {
        // when
        UserDTO dto = mapper.toDto(user);

        // then
        if (user == null) {
            assertNull(dto);
        } else {
            assertNotNull(dto);
            assertEquals(user.getId(), dto.getId());
            assertEquals(user.getUsername(), dto.getUsername());
        }
    }

    @ParameterizedTest
    @MethodSource("userDto")
    void toEntity_shouldMapUserDtoCorrectly(UserDTO dto) {
        // when
        User user = mapper.toEntity(dto);

        // then
        if (dto == null) {
            assertNull(user);
        } else {
            assertNotNull(user);
            assertEquals(dto.getId(), user.getId());
            assertEquals(dto.getUsername(), user.getUsername());
        }
    }

    static Stream<User> user() {
        User user = User.builder().build();
        user.setId(UUID.randomUUID());
        user.setUsername("Alice");

        return Stream.of(
                user,
                null
        );
    }

    static Stream<UserDTO> userDto() {
        UserDTO dto = new UserDTO();
        dto.setId(UUID.randomUUID());
        dto.setUsername("Alice");

        return Stream.of(
                dto,
                null
        );
    }
}

