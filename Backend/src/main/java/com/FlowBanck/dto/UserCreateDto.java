package com.FlowBanck.dto;

import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.Rol;
import com.FlowBanck.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Por favor, introduzca su Nombre")
    private String name;

    @NotEmpty(message = "Por favor, introduzca su Apellido")
    private String surname;

    @NotEmpty(message = "Por favor, introduzca una dirección de correo electrónico válida")
    @Email
    private String email;

    @NotEmpty(message = "Por favor, introduzca una contraseña")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    @NotEmpty(message = "Por favor, seleccione su Rol")
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
