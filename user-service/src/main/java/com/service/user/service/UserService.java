package com.service.user.service;

import com.service.user.dto.UserDTO;

public interface UserService {
    Long createUser(UserDTO userDTO);
    void updateUser(UserDTO userDTO);
}
