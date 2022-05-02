package com.api.roommate.controllers;

import java.util.List;

import javax.validation.Valid;

import com.api.roommate.models.house.House;
import com.api.roommate.models.house.HouseUser;
import com.api.roommate.models.task.HouseTask;
import com.api.roommate.models.task.HouseTaskData;
import com.api.roommate.models.task.HouseTaskRecord;
import com.api.roommate.models.task.HouseTaskUserRequest;
import com.api.roommate.models.task.TaskRequest;
import com.api.roommate.models.user.CoreUser;
import com.api.roommate.repository.HouseUserRepository;
import com.api.roommate.repository.HouseTaskRecordRepository;
import com.api.roommate.repository.HouseTaskRepository;
import com.api.roommate.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/tasks")
public class TaskController {

    @Autowired
    HouseTaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    HouseUserRepository houseUserRepository;

    @Autowired
    HouseTaskRecordRepository houseTaskRecordRepository;

    @PostMapping(path="/task")
    public ResponseEntity<?> createHouseTask(final @Valid @RequestBody HouseTaskData housetaskData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser owner = houseUserRepository.findByUserUuid(user.getUuid());
        House taskHouse = owner.getHouse();

        if (owner == null || !owner.getStatus().equals("owner")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        HouseTask newHouseTask = new HouseTask();
        newHouseTask.setName(housetaskData.getName());
        newHouseTask.setHouseUser(owner);
        taskHouse.addHouseTask(newHouseTask);
        taskRepository.save(newHouseTask);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path="house")
    public ResponseEntity<?> getHouseTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        House taskHouse = houseUser.getHouse();
        
        if (user != null && houseUser != null && taskHouse != null) {
            return ResponseEntity.ok(taskRepository.findByHouseId(taskHouse.getId()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="user")
    public ResponseEntity<?> getHouseTasksUser(final @Valid @RequestBody HouseTaskUserRequest userRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        House taskHouse = houseUser.getHouse();
        HouseTask task = taskRepository.findByUuid(userRequest.getTaskUUID());

        if (user == null || houseUser == null || taskHouse == null || task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!task.getHouse().getId().equals(taskHouse.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } 

        return ResponseEntity.ok(task.getHouseUser());
    }

    @GetMapping(path="me")
    public ResponseEntity<?> getMyTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        
        if (user != null && houseUser != null) {
            return ResponseEntity.ok(taskRepository.findByHouseUserId(houseUser.getId()));
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path="task")
    public ResponseEntity<?> getTask(final @Valid @RequestBody TaskRequest taskRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        HouseTask task = taskRepository.findByUuid(taskRequest.getTaskUUID());
        House house = houseUser.getHouse();
        List<HouseTask> houseTasks = house.getTasks();

        if (user == null || houseUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (houseTasks.contains(task)) {
            return ResponseEntity.ok(task);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping(path="task")
    public ResponseEntity<?> completeTask(final @Valid @RequestBody TaskRequest taskRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        HouseUser houseUser = houseUserRepository.findByUserUuid(user.getUuid());
        HouseTask task = taskRepository.findByUuid(taskRequest.getTaskUUID());
        List<HouseTask> activeTasks = houseUser.getActiveTasks();
        House house = houseUser.getHouse();
        List<HouseUser> houseUsers = house.getRoomates();

        if (user == null || houseUser == null || task == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (activeTasks.contains(task)) {
            HouseTaskRecord newHouseTaskRecord = new HouseTaskRecord();
            newHouseTaskRecord.setHouseTask(task);
            newHouseTaskRecord.setHouseUser(houseUser);
            if (houseUsers.size() > 0) {
                int current = houseUsers.indexOf(houseUser);
                int next = (current + 1) % houseUsers.size();
                task.setHouseUser(houseUsers.get(next));
            }
            houseTaskRecordRepository.save(newHouseTaskRecord);
            task.addHouseTaskRecord(newHouseTaskRecord);
            taskRepository.save(task);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
