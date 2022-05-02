package com.api.roommate.repository;

import java.util.UUID;

import com.api.roommate.models.house.House;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    House findByUuid(UUID houseUUID);
}
