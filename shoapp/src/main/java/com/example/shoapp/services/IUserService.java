package com.example.shoapp.services;

import com.example.shoapp.dtos.UserDTO;
import com.example.shoapp.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws Exception;

    String login(String phoneNumber, String password) throws Exception;
}
