package com.api.roommate.controllers;

import javax.validation.Valid;

import com.api.roommate.models.house.House;
import com.api.roommate.models.house.HouseData;
import com.api.roommate.models.house.HouseStatusData;
import com.api.roommate.models.house.HouseUser;
import com.api.roommate.models.house.HouseUserData;
import com.api.roommate.models.user.CoreUser;
import com.api.roommate.repository.HouseRepository;
import com.api.roommate.repository.HouseUserRepository;
import com.api.roommate.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/house")
public class HouseController {

    @Autowired 
    UserRepository userRepository;

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    HouseUserRepository houseUserRepository;

    @PostMapping(path="/create")
    public ResponseEntity<?> createHouse(final @Valid @RequestBody HouseData houseData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());

        if (houseUser == null) {
            houseUser = new HouseUser();
            houseUser.setUser(user);
        }

        if (houseUser.getHouse() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        House newHouse = new House();
        newHouse.setName(houseData.getName());
        newHouse.setOwner(houseUser);
        houseUser.setStatus("owner");
        newHouse.addRoomate(houseUser);
        houseRepository.save(newHouse);
        houseUserRepository.save(houseUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
        
    }

    @PostMapping(path="/join")
    public ResponseEntity<?> addUserToHouse(final @Valid @RequestBody HouseUserData houseUserData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        if (houseUser == null) {
            houseUser = new HouseUser();
            houseUser.setUser(user);
        }

        House house = houseRepository.findByUuid(houseUserData.getHouseUUID());

        if (house == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (houseUser.getHouse() != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        houseUser.setStatus("requested");
        house.addRoomate(houseUser);
        houseRepository.save(house);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(path="/status")
    public ResponseEntity<?> setHouseUserStatus(final @Valid @RequestBody HouseStatusData houseStatusData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser owner = houseUserRepository.findByUserUuid(user.getUuid());
        HouseUser statusUser = houseUserRepository.findByUserUuid(houseStatusData.getUserUUID());

        if (!owner.getStatus().equals("owner")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (owner.getHouse().getUuid() != statusUser.getHouse().getUuid()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        statusUser.setStatus(houseStatusData.getStatus());
        houseUserRepository.save(statusUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path="/houses")
    public @ResponseBody Iterable<House> getAllHouses() {
        return houseRepository.findAll();
    }

    @GetMapping(path="/house")
    public ResponseEntity<?> getHouse(final @Valid @RequestBody HouseUserData houseUserData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        House house = houseRepository.findByUuid(houseUserData.getHouseUUID());
        if (house == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(house);
    }
    
    @GetMapping(path="/me")
    public ResponseEntity<?> getMyHouse() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        if (user != null && houseUser != null && houseUser.getHouse() != null) {
            return ResponseEntity.ok(houseUser.getHouse());
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
