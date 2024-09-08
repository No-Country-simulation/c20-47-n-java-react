package com.FlowBanck.controller;

import com.FlowBanck.dto.UserCreateDto;
import com.FlowBanck.dto.UserDto;
import com.FlowBanck.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.getSave(userCreateDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> upDateUser(@Valid @RequestBody UserCreateDto userCreateDto, @PathVariable long id){
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.updateUser(userCreateDto,id));
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable long id){
        return this.userService.userDelete(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> customerById (@PathVariable long id){
        return ResponseEntity.ok(this.userService.searchUserById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> userAll (){
        return ResponseEntity.ok(this.userService.allUser());
    }
}
