package com.FlowBanck.service;

import com.FlowBanck.dto.UserCreateDto;
import com.FlowBanck.dto.UserDto;
import com.FlowBanck.entity.*;
import com.FlowBanck.exception.ResourceNotFoundException;
import com.FlowBanck.repository.RolRepository;
import com.FlowBanck.repository.StateRepository;
import com.FlowBanck.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

   private final UserRepository userRepository;
   private  final RolRepository rolRepository;
   private final PasswordEncoder passwordEncoder;
   private final BankAccountService bankAccountService;
   private final StateRepository stateRepository;

   //GUARDAR USUARIO
    public UserDto getSave(UserCreateDto userCreateDto) throws Exception {

        Set<Rol> roles = new HashSet<>();
        Set<State> states = new HashSet<>();

       // states.add(new State(EnumState.ACTIVA));

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

        Optional<State> stateOptional = this.stateRepository.findByState(EnumState.ACTIVA);

        if(stateOptional.isPresent()){
            states.add(stateOptional.get());
        }else {
            State newState = State.builder()
                    .state(EnumState.ACTIVA)
                    .build();
            this.stateRepository.save(newState);
            states.add(newState);
        }

        UserEntity userEntity = UserEntity.builder()
                .name(userCreateDto.getName())
                .surname(userCreateDto.getSurname())
                .email(userCreateDto.getEmail())
                .password(this.passwordEncoder.encode(userCreateDto.getPassword()))
                .state(states)
                .roles(roles)
                .build();

        UserEntity user = this.userRepository.save(userEntity);
        this.bankAccountService.saveBankAccount(user,userCreateDto.getAccountType());

        return UserDto.fromUserDto(user);

    }



    //EDITAR USUARIO
    public UserDto updateUser(UserCreateDto createDto, long id){
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Cliente","ID",id);
        }
        UserEntity userEntity = optionalUser.get();
        userEntity.setName(createDto.getName());
        userEntity.setSurname(createDto.getSurname());
        userEntity.setEmail(createDto.getEmail());
        userEntity.setPassword(createDto.getPassword());

        Set<Rol> rolExisting = userEntity.getRoles();
        rolExisting.clear();

        Set<Rol> roles = createDto.getRol().stream()
                .map(role -> {
                    Optional<Rol> existingRol = rolRepository.findByRol(EnumRol.valueOf(role.name()));
                    return existingRol.orElseGet(() -> {
                        Rol newRol = Rol.builder().rol(EnumRol.valueOf(role.name())).build();
                        return this.rolRepository.save(newRol);
                    });
                })
                .collect(Collectors.toSet());
        userEntity.setRoles(roles);
        return UserDto.fromUserDto(this.userRepository.save(userEntity));





    }

    //DESACTIVAR CUENTA
    public String userDelete (long id){
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isEmpty()){
            throw new ResourceNotFoundException("Cliente","ID",id);
        }
        UserEntity userEntity = optionalUser.get();
        Set<State> states = new HashSet<>();
        states.add(new State(EnumState.INACTIVA));
        userEntity.setState(states);
        this.userRepository.save(userEntity);
        return "El usuario se elimino correctamente";
    }




    //BUSCAR USUARIO POR ID
    public UserDto searchUserById(Long id) {

        Optional<UserEntity> userEntityOptional = this.userRepository.findById(id);

        if (userEntityOptional.isEmpty()) {
            throw  new ResourceNotFoundException("Cliente","ID", id);
        }
        return UserDto.fromUserDto(userEntityOptional.get());
    }



    //LISTAR TODOS LOS USUARIOS
    public List<UserDto> allUser(){

        List<UserEntity> userEntityList = this.userRepository.findAll();

        if(userEntityList.isEmpty()){
            throw new ResourceNotFoundException("clientes");
        }
        return UserDto.ListUserDto(userEntityList);

    }

}
