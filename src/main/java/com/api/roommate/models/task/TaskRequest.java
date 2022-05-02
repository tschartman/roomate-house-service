package com.api.roommate.models.task;

import java.io.Serializable;
import java.util.UUID;

public class TaskRequest implements Serializable {
  private UUID taskUUID;

  public UUID getTaskUUID() {
    return this.taskUUID;
  }

  public void setTaskUUID(UUID taskUUID) {
    this.taskUUID = taskUUID;
  }

}