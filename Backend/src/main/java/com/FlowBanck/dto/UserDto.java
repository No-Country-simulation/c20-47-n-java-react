package com.FlowBanck.dto;

import com.FlowBanck.entity.EnumRol;
import com.FlowBanck.entity.Rol;
import com.FlowBanck.entity.UserEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String state;

    private Set<EnumRol> rol;

    public static UserDto fromUserDto(UserEntity userEntity) {
        return UserDto.builder()
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .email(userEntity.getEmail())
                .rol(userEntity.getRoles().stream().map(Rol::getRol).collect(Collectors.toSet()))
                .state(userEntity.getState())
                .build();
    }

    public static List<UserDto> ListUserDto(List<UserEntity> list) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (UserEntity listUserEntity : list) {
            UserDto userDto = UserDto.builder()
                    .name(listUserEntity.getName())
                    .surname(listUserEntity.getSurname())
                    .email(listUserEntity.getEmail())
                    .rol(listUserEntity.getRoles().stream().map(Rol::getRol).collect(Collectors.toSet()))
                    .build();
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

}



