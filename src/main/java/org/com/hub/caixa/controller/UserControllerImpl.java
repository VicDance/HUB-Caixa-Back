package org.com.hub.caixa.controller;

import com.hub.caixa.api.UsersApi;
import com.hub.caixa.model.UserDTO;
import org.com.hub.caixa.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserControllerImpl implements UsersApi {
    private final UserService userService;

    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserDTO> createUser(UserDTO userDTO) {
        UserDTO user = userService.createUser(userDTO);
        return ResponseEntity.ok(user);
    }
}
