package com.api.roommate.models.task;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class HouseTaskData implements Serializable {
    
    @NotEmpty(message = "house task name can not be empty")
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
