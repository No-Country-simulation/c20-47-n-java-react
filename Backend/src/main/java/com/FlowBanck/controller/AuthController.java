package com.FlowBanck.controller;

import com.FlowBanck.dto.LoginDto;
import com.FlowBanck.security.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginDto loginDto){

        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(),
                            loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateAccesTokem(authentication.getName());
            Map<String, Object> response = new HashMap<>();
            response.put("Token",jwt);
            response.put("Message","Autenticación exitosa");
            response.put("Email",authentication.getName());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch (Exception e){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales invalidas");
        }
// ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas")
    }
}
