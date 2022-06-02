package com.example.quangca.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChapterDto {

    private int id;

    private String number;

    private String name;

    private String content;

    private int novelId;
}
