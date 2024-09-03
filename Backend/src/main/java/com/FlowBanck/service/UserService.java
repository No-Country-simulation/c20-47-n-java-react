package com.FlowBanck.service;

import com.FlowBanck.dto.UserCreateDto;
import com.FlowBanck.dto.UserDto;
import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.Rol;
import com.FlowBanck.entity.UserEntity;
import com.FlowBanck.repository.RolRepository;
import com.FlowBanck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private  final RolRepository rolRepository;
   private final PasswordEncoder passwordEncoder;
   private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserDto getSave(UserCreateDto userCreateDto) throws Exception {

        String state="";
        Set<Rol> roles = new HashSet<>();
        for (EnumRol enumRol: userCreateDto.getRol()){
            Optional<Rol> existingRol = this.rolRepository.findByRol(enumRol);
            if (existingRol.isPresent()){
                roles.add(existingRol.get());
            }else {
                Rol newRol = Rol.builder()
                        .rol(enumRol)
                        .build();
                this.rolRepository.save(newRol);
                roles.add(newRol);
            }
        }
        UserEntity userEntity = UserEntity.builder()
                .name(userCreateDto.getName())
                .surname(userCreateDto.getSurname())
                .email(userCreateDto.getEmail())
                .password(this.passwordEncoder.encode(userCreateDto.getPassword()))
                .state(state="activo".toUpperCase(Locale.ROOT))
                .roles(roles)
                .build();
        try {
            this.userRepository.save(userEntity);
            return UserDto.fromUserDto(userEntity);
        }catch (Exception e){
            logger.error("Error saving userEntity: {}", e.getMessage(), e);
            throw e;
        }
    }



}
