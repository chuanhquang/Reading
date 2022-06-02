package com.example.quangca.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class NovelDto {

    private int id;

    private String name;

    private String description;

    private List<GenreDto> genres;

    private AuthorDto author;

    private String thumbnail;

//    private List<Chapter> chapters;
    private int totalChapter;
}
