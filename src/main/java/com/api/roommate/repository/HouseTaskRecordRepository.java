package com.api.roommate.repository;

import java.util.List;
import java.util.UUID;

import com.api.roommate.models.task.HouseTaskRecord;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseTaskRecordRepository extends JpaRepository<HouseTaskRecord, Long> {
  HouseTaskRecord findByUuid(UUID housTaskRecordUUID);
  List<HouseTaskRecord> findByHouseTaskId(Long houseTaskId);
}
