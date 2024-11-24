package com.mikolajjanik.hospital_catering_admin.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginUserDTO {

    @NotNull(message = "Field 'email' cannot be null.")
    @NotBlank(message = "Field 'email' cannot be empty.")
    @Email(message = "Field 'email': Provided string is not an email.")
    private String email;

    @NotNull(message = "Field 'password' cannot be null.")
    @NotBlank(message = "Field 'password' cannot be empty.")
    private String password;

}
