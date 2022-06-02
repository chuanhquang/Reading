package com.example.quangca.controllers.api;

import com.example.quangca.dto.UserDto;
import com.example.quangca.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        UserDto newUser = userService.signupMember(userDto);
        if (newUser == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(newUser);
    }
}
