package com.api.roommate.models.house;

import java.io.Serializable;
import java.util.UUID;

public class HouseStatusData implements Serializable {
    
    private UUID userUUID;
    private UUID houseUUID;
    private String status;

    public UUID getUserUUID() {
        return this.userUUID;
    }

    public void setUserUUID(UUID userUUID) {
        this.userUUID = userUUID;
    }

    public UUID getHouseUUID() {
        return this.houseUUID;
    }

    public void setHouseUUID(UUID houseUUID) {
        this.houseUUID = houseUUID;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
