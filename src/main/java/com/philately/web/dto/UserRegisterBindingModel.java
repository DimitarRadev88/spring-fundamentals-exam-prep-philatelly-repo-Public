package com.philately.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterBindingModel {

    @Size(min = 3, max = 20, message = "Username length must be between 3 and 20 characters!")
    private String username;
    @NotNull(message = "Email cannot be empty!")
    @Email(message = "Please enter valid email!")
    private String email;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String password;
    @Size(min = 3, max = 20, message = "Password length must be between 3 and 20 characters!")
    private String confirmPassword;

}
