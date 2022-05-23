package com.api.roommate.models.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.api.roommate.models.task.HouseTask;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="houses")
public class House implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID uuid = UUID.randomUUID();

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private List<HouseUser> roommates = new ArrayList<HouseUser>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "house", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private List<HouseTask> tasks = new ArrayList<HouseTask>();

    @OneToOne(cascade = CascadeType.ALL)
    private HouseUser owner;

    public House() {
    }
    
    public UUID getUuid() {
        return this.uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public List<HouseUser> getRoommates() {
        return this.roommates;
    }

    public void setRoomates(List<HouseUser> roommates) {
        this.roommates = roommates;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addRoommate(HouseUser houseUser) {
        roommates.add(houseUser);
        houseUser.setHouse(this);
    }

    public void removeRoommate(HouseUser houseUser) {
        roommates.remove(houseUser);
        houseUser.setHouse(null);
    }

    public void addHouseTask(HouseTask houseTask) {
        tasks.add(houseTask);
        houseTask.setHouse(this);
    }

    public void removeHouseTask(HouseTask houseTask) {
        tasks.remove(houseTask);
        houseTask.setHouse(null);
    }

    public List<HouseTask> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<HouseTask> tasks) {
        this.tasks = tasks;
    }


    public HouseUser getOwner() {
        return this.owner;
    }

    public void setOwner(HouseUser owner) {
        this.owner = owner;
    }
}
