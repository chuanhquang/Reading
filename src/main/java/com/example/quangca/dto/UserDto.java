package com.example.quangca.dto;

import com.example.quangca.models.Novel;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Accessors(chain = true)
public class UserDto {

    private int id;

    @NotBlank(message = "Username không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private Set<RoleDto> roles = new HashSet<>();

    private List<Novel> favoritedNovels = new ArrayList<>();
}
