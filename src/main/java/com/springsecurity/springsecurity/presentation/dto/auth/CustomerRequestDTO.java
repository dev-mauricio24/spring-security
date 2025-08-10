package com.springsecurity.springsecurity.presentation.dto.auth;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String documentType;
    private String document;
    private String phone;
    private String address;
    private UserRequestDTO userRequestDTO;
}
