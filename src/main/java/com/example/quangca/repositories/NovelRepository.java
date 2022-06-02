package com.example.quangca.repositories;

import com.example.quangca.models.Novel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface NovelRepository extends JpaRepository<Novel, Integer> {
    Novel findNovelByName(String name);

    @Query("SELECT n FROM Novel n, User u WHERE u.username = :username")
    Set<Novel> findFavoritedNovelsByUsername(@Param("username") String username);

//    @Query("SELECT n FROM Novel n, NovelGenre ng, Genre g WHERE g.id = :genreId AND g.id = ng.genre_id AND ng.novel_id = n.id")
    @Query("SELECT n FROM Novel n " +
            "JOIN n.genres g " +
            "WHERE g.id = :genreId")
    List<Novel> findNovelsByGenreId(@Param("genreId") int genreId);
}
