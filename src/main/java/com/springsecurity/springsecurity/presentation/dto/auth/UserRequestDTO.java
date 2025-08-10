package com.springsecurity.springsecurity.presentation.dto.auth;

import com.springsecurity.springsecurity.utils.enums.RoleEnum;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDTO {
    private Long id;
    private String username;
    private String password;
    private boolean enabled;
    private boolean accountNoExpired;
    private boolean accountNoLocked;
    private boolean credentialNoExpired;
    private Set<RoleEnum> roles;
}
