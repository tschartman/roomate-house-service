package com.api.roommate.models.house;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class HouseData implements Serializable {
    
    @NotEmpty(message = "house name can not be empty")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
