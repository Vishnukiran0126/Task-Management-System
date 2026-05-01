package com.example.Task.service;

import com.example.Task.entites.User;
import com.example.Task.repositories.UserRepository;
import com.example.Task.utils.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    MyUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u=userRepository.getUserByName(username);
        System.out.println(u.getName());
        if(u==null){
            //no user
            System.out.println("User not found");

        }
        //UserDetails is n interface so using UserPrincipal to provide implementation for it

        return new UserPrincipal(u);
    }
}
