package com.springsecurity.springsecurity.service.interfaces;

import com.springsecurity.springsecurity.presentation.dto.auth.CustomerRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserResponseDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IAuthenticationService {
    public UserResponseDTO registerUser(UserRequestDTO request);
    public UserResponseDTO registerCustomer(CustomerRequestDTO request);
}
