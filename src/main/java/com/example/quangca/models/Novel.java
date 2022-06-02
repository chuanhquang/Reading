package com.example.quangca.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Accessors(chain = true)
public class Novel {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(targetEntity = Genre.class, cascade=CascadeType.MERGE)
    //@JoinColumn(name="novel_id")
    @JoinTable(
            name = "novel_genre",
            joinColumns = @JoinColumn(name = "novel_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres = new ArrayList<>();

    @ManyToOne(targetEntity = Author.class, cascade=CascadeType.MERGE)
    @JoinColumn(name="author_id")
    private Author author;

    @Column(name = "thumbnail")
    private String thumbnail;

    @ManyToMany(mappedBy = "favoritedNovels", cascade = CascadeType.ALL)
    @JsonIgnore
//    @JsonIgnoreProperties({"roles", "password", "hibernateLazyInitializer", "handler"})
    private Set<User> users = new HashSet<>();

}
