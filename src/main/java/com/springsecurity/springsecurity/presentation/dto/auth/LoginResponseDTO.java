package com.springsecurity.springsecurity.presentation.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username, message, token, status"})
public record LoginResponseDTO(String username,
                               String message,
                               String token,
                               boolean status) {
}
