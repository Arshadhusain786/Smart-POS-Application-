package com.arshad.smart_pos_system.mapper;

import com.arshad.smart_pos_system.dto.UserDto;
import com.arshad.smart_pos_system.model.User;

public class UserMapper
{

    public static UserDto toDto(User savedUser)
    {
        UserDto userDto = new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setFullName(savedUser.getFullName());
        userDto.setEmail(savedUser.getEmail());
        userDto.setRole(savedUser.getRole());
        userDto.setPhone(savedUser.getPhone());
        userDto.setCreatedAt(savedUser.getCreatedAt());
        userDto.setUpdatedAt(savedUser.getUpdatedAt());
        userDto.setLastLogin(savedUser.getLastLogin());

        return userDto;
    }
}
