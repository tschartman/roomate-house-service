package com.api.roommate.models.user;

import java.io.Serializable;

public class UserAuthenticationResponse implements Serializable {
    private String jwt;

    public UserAuthenticationResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}