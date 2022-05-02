package com.api.roommate.controllers;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.roommate.models.user.CoreUser;
import com.api.roommate.models.user.UserAuthenticationRequest;
import com.api.roommate.models.user.UserAuthenticationResponse;
import com.api.roommate.models.user.UserData;
import com.api.roommate.models.user.UserAuthentication;
import com.api.roommate.repository.UserAuthenticationRepository;
import com.api.roommate.repository.UserRepository;
import com.api.roommate.service.user.DefaultUserService;
import com.api.roommate.util.JwtUtil;

@Controller
@RequestMapping(path="/authentication")
public class AuthenticationController {

    @Autowired
    DefaultUserService defaultUserService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping(path="/register")
    public ResponseEntity<?> userRegistration (final @Valid @RequestBody UserData userData) {
        if (userRepository.findByEmail(userData.getEmail()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        CoreUser newUser = new CoreUser();
        UserAuthentication userAuthentication = new UserAuthentication();
        BeanUtils.copyProperties(userData, newUser);
        userAuthentication.setEmail(userData.getEmail());
        userAuthentication.setPassword(passwordEncoder.encode(userData.getPassword()));
        userAuthentication.setUser(newUser);
        newUser.setUserAuthentication(userAuthentication);
        userRepository.save(newUser);
        userAuthenticationRepository.save(userAuthentication);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(path="/authenticate")
    public ResponseEntity<?> authenticate(final @Valid @RequestBody UserAuthenticationRequest userAuthentication) {

        if (userRepository.findByEmail(userAuthentication.getEmail()) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(userAuthentication.getEmail(), userAuthentication.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        UserDetails userDetails = defaultUserService.loadUserByUsername(userAuthentication.getEmail());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new UserAuthenticationResponse(jwt));
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<CoreUser> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path="/me")
    public ResponseEntity<?> getMyAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        CoreUser user = userRepository.findByEmail(currentUserName);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(user);
    }
}
