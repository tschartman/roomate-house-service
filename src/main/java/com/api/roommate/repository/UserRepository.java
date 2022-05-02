package com.api.roommate.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import com.api.roommate.models.user.CoreUser;

@Repository
public interface UserRepository extends JpaRepository<CoreUser, Long> {
    CoreUser findByEmail(String email);
    CoreUser findByUuid(UUID userUUID);
}
