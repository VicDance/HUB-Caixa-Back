package org.com.hub.caixa.service;

import com.hub.caixa.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.com.hub.caixa.mapper.UserMapper;
import org.com.hub.caixa.model.User;
import org.com.hub.caixa.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDTO createUser(UserDTO dto) {
        User user = userMapper.toEntity(dto);
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
