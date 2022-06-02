package com.example.quangca.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "novel_genre")
public class NovelGenre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "novel_id")
    private int novelId;

    @Column(name = "genre_id")
    private int genreId;
}
