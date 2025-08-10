package com.springsecurity.springsecurity.service.implementation;

import com.springsecurity.springsecurity.persistence.entity.RoleEntity;
import com.springsecurity.springsecurity.persistence.entity.UserEntity;
import com.springsecurity.springsecurity.persistence.repository.CustomerRepository;
import com.springsecurity.springsecurity.persistence.repository.RoleRepository;
import com.springsecurity.springsecurity.persistence.repository.UserRepository;
import com.springsecurity.springsecurity.presentation.dto.auth.CustomerRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserResponseDTO;
import com.springsecurity.springsecurity.service.interfaces.IAuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username is already in use");

        Set<RoleEntity> roles = roleRepository.findByRoleIn(request.getRoles());

        if(roles.size() != request.getRoles().size())
            throw new RuntimeException("One or more roles does not exists");

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(roles)
                .build();

        UserEntity userSaved = userRepository.save(user);

        return UserResponseDTO.builder()
                .username(userSaved.getUsername())
                .codOperation("A00")
                .message("User registered successfully")
                .build();
    }

    @Override
    public UserResponseDTO registerCustomer(CustomerRequestDTO request) {
        return null;
    }
}
