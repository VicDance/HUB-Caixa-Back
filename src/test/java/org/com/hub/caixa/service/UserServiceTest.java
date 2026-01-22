package org.com.hub.caixa.service;

import com.hub.caixa.model.UserDTO;
import org.com.hub.caixa.mapper.UserMapper;
import org.com.hub.caixa.model.User;
import org.com.hub.caixa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    void createUser_shouldMapSaveAndReturnDto() {
        // given
        UserDTO dto = new UserDTO();
        User user = new User();
        UserDTO expectedDto = new UserDTO();

        // when
        when(userMapper.toEntity(dto)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(expectedDto);

        UserDTO result = userService.createUser(dto);

        // then
        assertNotNull(result);
        assertEquals(expectedDto, result);

        verify(userMapper).toEntity(dto);
        verify(userRepository).save(user);
        verify(userMapper).toDto(user);
    }
}