package com.api.roommate.models.task;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.api.roommate.models.house.HouseUser;
import com.fasterxml.jackson.annotation.JsonBackReference;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

@Entity
@Table(name="task_record")
public class HouseTaskRecord implements Serializable {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid = UUID.randomUUID();

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  optional = false)
  @JoinColumn(name="houseTask_id")
  @JsonBackReference
  private HouseTask houseTask;
  
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  optional = false)
  private HouseUser houseUser;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "timestamp")
  private Date timestamp;

  public HouseTaskRecord() {
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

  public HouseTask getHouseTask() {
    return this.houseTask;
  }

  public void setHouseTask(HouseTask houseTask) {
    this.houseTask = houseTask;
  }

  public HouseUser getHouseUser() {
    return this.houseUser;
  }

  public void setHouseUser(HouseUser houseUser) {
    this.houseUser = houseUser;
  }

  public Date getTimestamp() {
    return this.timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

}
