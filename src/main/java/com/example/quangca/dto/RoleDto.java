package com.example.quangca.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleDto {

    private String name;

//    private Set<User> users = new HashSet<>();
}
