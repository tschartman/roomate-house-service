package com.api.roommate.models.task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.api.roommate.models.house.House;
import com.api.roommate.models.house.HouseUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import org.hibernate.annotations.Type;

@Entity
@Table(name="tasks")
public class HouseTask implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid = UUID.randomUUID();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name="house_id")
    @JsonBackReference
    private House house;

    private String name;

    private String color;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name="houseUser_id")
    private HouseUser houseUser;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="houseTaskRecord_id")
    @JsonManagedReference
    private List<HouseTaskRecord> houseTaskRecords = new ArrayList<HouseTaskRecord>();
    
    public HouseTask() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public House getHouse() {
        return this.house;
    }

    public void setHouse(House house) {
        this.house = house;
    }

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

    public HouseUser getHouseUser() {
        return this.houseUser;
    }

    public void setHouseUser(HouseUser houseUser) {
        this.houseUser = houseUser;
    }

    public List<HouseTaskRecord> getHouseTaskRecords() {
        return this.houseTaskRecords;
    }

    public void addHouseTaskRecord(HouseTaskRecord houseTaskRecord) {
        houseTaskRecords.add(houseTaskRecord);
    }

}
