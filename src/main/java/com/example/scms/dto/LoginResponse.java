package com.example.scms.dto;

import com.example.scms.entity.enums.UserRole;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String tokenType = "Bearer";
    private String accessToken;
    private String username;
    private UserRole role;
}
