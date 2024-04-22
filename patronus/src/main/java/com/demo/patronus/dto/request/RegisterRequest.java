package com.demo.patronus.dto.request;

import com.demo.patronus.annotation.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    private String username;

    @NotBlank
    @Password
    private String password;

    @NotBlank
    private String name;

    @Email
    private String email;

}
