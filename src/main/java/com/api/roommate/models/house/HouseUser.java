package com.api.roommate.models.house;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.api.roommate.models.user.CoreUser;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="house_users")
public class HouseUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private CoreUser user;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name="house_id")
    @JsonBackReference
    private House house;

    private String status;

    public HouseUser() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CoreUser getUser() {
        return this.user;
    }

    public void setUser(CoreUser user) {
        this.user = user;
    }

    public House getHouse() {
        return this.house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
