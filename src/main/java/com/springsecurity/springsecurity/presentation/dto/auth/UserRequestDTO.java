package com.springsecurity.springsecurity.presentation.dto.auth;

import com.springsecurity.springsecurity.utils.enums.RoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Validated
public class UserRequestDTO {
    @NotBlank(message = "El nombre de usuario no debe estar vacío")
    private String username;
    @NotBlank(message = "La contraseña no debe estar vacía")
    private String password;
    @NotNull(message = "Debe enviar al menos un rol")
    private Set<RoleEnum> roles;
}
