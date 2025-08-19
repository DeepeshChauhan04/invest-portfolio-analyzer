package com.sharemarket.invest.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotBlank(message = "User name must not be blank")
    @JsonProperty(value ="username")
    private String username;

    @Email(message = "Email is required")
    @JsonProperty(value="email")
    private String email;

    @NotBlank(message = "password  must not be blank")
    @JsonProperty(value="password")
    private String passwordHash;

    @NotBlank(message = "Full name must not be blank")
    @JsonProperty(value = "fullName")
    private String fullName;
}

