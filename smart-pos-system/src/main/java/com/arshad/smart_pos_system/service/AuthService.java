package com.arshad.smart_pos_system.service;

import com.arshad.smart_pos_system.dto.UserDto;
import com.arshad.smart_pos_system.payload.response.AuthResponse;

public interface AuthService
{
    AuthResponse signup(UserDto userDto) throws Exception;

    AuthResponse login(UserDto userDto);


}
