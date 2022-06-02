package com.example.quangca.dto;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GenreDto {

    private int id;

    private String name;

    private String description;
}
