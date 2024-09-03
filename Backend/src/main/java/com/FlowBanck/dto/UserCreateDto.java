package com.FlowBanck.dto;

import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.Rol;
import com.FlowBanck.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserCreateDto {

    @NotEmpty(message = "Debe ingresar un nombre")
    private String name;

    @NotEmpty(message = "Debe ingresar un Apellido")
    private String surname;

    @NotEmpty(message = "Debe ingresar un email")
    @Email(message = "El email que ah ongresado es incorrecto")
    private String email;

    @NotEmpty(message = "Debe ingresar una contraseña")
    private String password;

    @NotEmpty(message = "Debe ingresar un rol")
    private Set<EnumRol> rol;

    public static UserDto from(UserEntity userEntity){

        return UserDto.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .rol(userEntity.getRoles().stream().map(Rol::getRol).collect(Collectors.toSet()))
                .build();
    }
}
