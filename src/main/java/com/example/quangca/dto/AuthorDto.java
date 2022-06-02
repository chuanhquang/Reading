package com.example.quangca.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorDto {

    private int id;

    private String name;

    private String description;
}
