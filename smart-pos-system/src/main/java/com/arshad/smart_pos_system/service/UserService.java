package com.arshad.smart_pos_system.service;

import com.arshad.smart_pos_system.model.User;

import java.util.List;

public interface UserService
{

  //  User getUserFromJwtToken(String email);
    User getCurrentUser();
    User getUserByEmail(String email);
    User getUserById(Long id);
    List<User> getAllUser();

}
