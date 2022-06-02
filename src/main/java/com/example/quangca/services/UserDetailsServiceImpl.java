package com.example.quangca.services;

import com.example.quangca.models.Role;
import com.example.quangca.models.User;
import com.example.quangca.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    //@Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        List<GrantedAuthority> grandAuthorities = new ArrayList<>();
        Set<Role> roles = user.getRoles();
        if(!roles.isEmpty()){
            for(Role role: roles){
                GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                grandAuthorities.add(authority);
            }
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grandAuthorities);
    }
}
