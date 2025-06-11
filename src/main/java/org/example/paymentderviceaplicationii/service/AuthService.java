package org.example.paymentderviceaplicationii.service;

import org.example.paymentderviceaplicationii.model.dto.LoginDTO;
import org.example.paymentderviceaplicationii.model.dto.UserDTO;

public interface AuthService {
    String registerUser(UserDTO userDTO);

    String loginUser(LoginDTO loginDTO);
}
