package com.api.roommate.models.user;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.api.roommate.util.ToLowerCaseConverter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class UserData implements Serializable {

    @NotEmpty(message = "Nick name can not be empty")
    private String nickname;

    @NotEmpty(message = "Email can not be empty")
    @Email(message = "Please provide a valid email")
    @JsonDeserialize(converter = ToLowerCaseConverter.class)
    private String email;

    @NotEmpty(message = "Password can not be empty")
    private String password;

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

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
