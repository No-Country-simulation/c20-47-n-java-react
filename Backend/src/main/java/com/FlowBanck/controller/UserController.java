package com.FlowBanck.controller;

import com.FlowBanck.dto.UserCreateDto;
import com.FlowBanck.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserCreateDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) throws Exception {
        return ResponseEntity.ok(this.userService.getSave(userCreateDto));
    }
}
