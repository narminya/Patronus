package com.demo.patronus.dto.request;

import com.demo.patronus.annotation.Password;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @Email
    private String email;

    @Password
    private String password;

}
