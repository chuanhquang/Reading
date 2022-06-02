package com.example.quangca.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user_novel")
public class NovelFavorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "novel_id")
    private int novelId;
}
