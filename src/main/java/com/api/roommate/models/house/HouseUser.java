package com.api.roommate.models.house;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.api.roommate.models.user.CoreUser;
import com.api.roommate.models.task.HouseTask;
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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "houseUser", cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonBackReference
    private List<HouseTask> activeTasks = new ArrayList<HouseTask>();

    private String status;


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

    public void addActiveTask(HouseTask activeTask) {
        activeTasks.add(activeTask);
    }

    public void removeActiveTask(HouseTask activeTask) {
        activeTasks.remove(activeTask);
    }


    public List<HouseTask> getActiveTasks() {
        return this.activeTasks;
    }

    public void setActiveTasks(List<HouseTask> activeTasks) {
        this.activeTasks = activeTasks;
    }

}
