package com.api.roommate.repository;

import java.util.UUID;

import com.api.roommate.models.house.HouseUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseUserRepository extends JpaRepository<HouseUser, Long> {
    HouseUser findByUserUuid(UUID userUUID);
}
