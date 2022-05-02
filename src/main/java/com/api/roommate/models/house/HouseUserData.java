package com.api.roommate.models.house;

import java.io.Serializable;
import java.util.UUID;

public class HouseUserData implements Serializable {
    
    private UUID houseUUID; 

    public UUID getHouseUUID() {
        return this.houseUUID;
    }

    public void setHouseUUID(UUID houseUUID) {
        this.houseUUID = houseUUID;
    }
    
}
