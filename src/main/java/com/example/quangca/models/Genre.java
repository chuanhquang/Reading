package com.example.quangca.models;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity
@Accessors(chain = true)
public class Genre {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String description;

//    @ManyToMany(mappedBy = "genres", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private Set<Novel> novels = new HashSet<>();
}
