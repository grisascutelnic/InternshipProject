package com.example.project.service;

import com.example.project.dto.UserDto;
import com.example.project.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}
