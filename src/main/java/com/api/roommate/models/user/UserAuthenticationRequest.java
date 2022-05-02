package com.api.roommate.models.user;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class UserAuthenticationRequest implements Serializable {

    @NotEmpty(message = "Email can not be empty")
    private String email;

    @NotEmpty(message = "Password can not be empty")
    private String password;


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
