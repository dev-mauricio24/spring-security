package com.springsecurity.springsecurity.service.implementation;

import com.springsecurity.springsecurity.persistence.entity.RoleEntity;
import com.springsecurity.springsecurity.persistence.entity.UserEntity;
import com.springsecurity.springsecurity.persistence.repository.RoleRepository;
import com.springsecurity.springsecurity.persistence.repository.UserRepository;
import com.springsecurity.springsecurity.presentation.dto.auth.LoginRequestDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.LoginResponseDTO;
import com.springsecurity.springsecurity.presentation.dto.auth.UserRequestDTO;
import com.springsecurity.springsecurity.utils.helpers.JWTUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role ->
                        authorities.add(
                                new SimpleGrantedAuthority("ROLE_" + role)));

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission ->
                        authorities.add(
                                new SimpleGrantedAuthority(permission.getName())));

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnabled(),
                userEntity.isAccountNoExpired(),
                userEntity.isCredentialNoExpired(),
                userEntity.isAccountNoLocked(),
                authorities
        );
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication authentication = authenticate(request.getUsername(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtils.generateToken(authentication);

        return  new LoginResponseDTO(request.getUsername(), "Login successful", token, true);
    }

    public LoginResponseDTO register(UserRequestDTO request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException("Username is already in use");

        Set<RoleEntity> roles = roleRepository.findByRoleIn(request.getRoles());

        if(roles.isEmpty())
            throw new RuntimeException("Roles does not exists");

        UserEntity user = UserEntity.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountNoExpired(true)
                .accountNoLocked(true)
                .credentialNoExpired(true)
                .roles(roles)
                .build();

        UserEntity userCreated = userRepository.save(user);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userCreated.getRoles().forEach(role ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().name())));

        userCreated.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorities);
        String token = jwtUtils.generateToken(authentication);

        return new LoginResponseDTO(request.getUsername(), "User registered successfully", token, true);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);

        if(userDetails == null)
            throw new BadCredentialsException("Invalid username or password");

        if(!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new BadCredentialsException("Invalid username or password");

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
