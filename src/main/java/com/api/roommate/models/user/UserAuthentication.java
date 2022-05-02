package com.api.roommate.models.user;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name="user_authentication")
public class UserAuthentication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userAuthenticationId;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne(mappedBy = "userAuthentication")
    @JsonManagedReference
    private CoreUser user;


    public UserAuthentication() {
    }

    public Long getUserAuthenticationId() {
        return this.userAuthenticationId;
    }

    public void setUserAuthenticationId(Long userAuthenticationId) {
        this.userAuthenticationId = userAuthenticationId;
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

    public CoreUser getUser() {
        return this.user;
    }

    public void setUser(CoreUser user) {
        this.user = user;
    }

}
