package com.api.roommate.models.task;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

public class HouseTaskData implements Serializable {
    
    @NotEmpty(message = "house task name can not be empty")
    private String name;

    private String color;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
