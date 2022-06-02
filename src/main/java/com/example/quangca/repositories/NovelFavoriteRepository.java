package com.example.quangca.repositories;

import com.example.quangca.models.NovelFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface NovelFavoriteRepository extends JpaRepository<NovelFavorite, Integer> {

    Set<NovelFavorite> findNoveFavoritelByUserId(int userId);
}
