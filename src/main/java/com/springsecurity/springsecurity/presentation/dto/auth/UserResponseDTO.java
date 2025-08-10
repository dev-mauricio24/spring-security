package com.springsecurity.springsecurity.presentation.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserResponseDTO {
    private String username;
    private String codOperation;
    private String message;
}
