package org.com.hub.caixa.service;

import com.hub.caixa.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.com.hub.caixa.mapper.UserMapper;
import org.com.hub.caixa.model.User;
import org.com.hub.caixa.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserDTO createUser(UserDTO dto) {
        User user = userMapper.tooEntity(dto);
        userRepository.save(user);
        return userMapper.tooDto(user);
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::tooDto)
                .collect(Collectors.toList());
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
