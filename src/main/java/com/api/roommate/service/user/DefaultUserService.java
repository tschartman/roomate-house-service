package com.api.roommate.service.user;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.api.roommate.repository.UserAuthenticationRepository;
import com.api.roommate.models.user.UserAuthentication;

@Service("userService")
public class DefaultUserService implements UserDetailsService {

    @Autowired
    private UserAuthenticationRepository userAuthenticationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAuthentication userAuthentication = userAuthenticationRepository.findByEmail(username);
        if (userAuthentication == null) {
            throw new UsernameNotFoundException("User Authentication with email does not exist");
        }
        return new User(userAuthentication.getEmail(), userAuthentication.getPassword(), new ArrayList<>());
    }
}
