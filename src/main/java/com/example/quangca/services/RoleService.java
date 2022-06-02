package com.example.quangca.services;

import com.example.quangca.models.Role;
import com.example.quangca.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findRoleById(int roleId){
        return roleRepository.getById(roleId);
    }

    public Role findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
