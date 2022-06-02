package com.example.quangca.repositories;

import com.example.quangca.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    @Query("SELECT r FROM Role r, User u WHERE u.username = :username")
    Set<Role> findRolesByUsername(@Param("username") String username);

    Role findRoleByName(String name);

}
