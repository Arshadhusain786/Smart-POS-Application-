package com.arshad.smart_pos_system.payload.response;

import com.arshad.smart_pos_system.dto.UserDto;
import lombok.Data;

@Data
public class AuthResponse
{
    private String jwt;
    private String message;
    private UserDto user;

}
