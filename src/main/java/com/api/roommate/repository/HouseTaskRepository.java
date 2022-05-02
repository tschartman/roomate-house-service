package com.api.roommate.repository;

import java.util.List;
import java.util.UUID;

import com.api.roommate.models.task.HouseTask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseTaskRepository extends JpaRepository<HouseTask, Long> {
    HouseTask findByUuid(UUID taskUUID);
    List<HouseTask> findByHouseId(Long houseId);
    List<HouseTask> findByHouseUserId(Long houseUserId);
}
