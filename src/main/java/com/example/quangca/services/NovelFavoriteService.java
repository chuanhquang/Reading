package com.example.quangca.services;

import com.example.quangca.models.NovelFavorite;
import com.example.quangca.repositories.NovelFavoriteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class NovelFavoriteService {
    @Autowired
    private NovelFavoriteRepository novelFavoriteRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     *
     * @param userId
     * @param novelId
     * @return True if added, otherwise if removed
     */
    public boolean addRemoveFavoriteNovel(int userId, int novelId){
        Set<NovelFavorite> favoriteNovels = novelFavoriteRepository.findNoveFavoritelByUserId(userId);
        List<NovelFavorite> result = favoriteNovels.stream().filter(novelFavorite -> novelFavorite.getNovelId() == novelId).toList();
        if(result.isEmpty()){
            NovelFavorite newNovelFavorite = new NovelFavorite();
            newNovelFavorite.setUserId(userId);
            newNovelFavorite.setNovelId(novelId);
            addFavoriteNovel(newNovelFavorite);
            return true;
        } else {
            removeFavoriteNovel(result.get(0).getId());
            return false;
        }
    }

    public NovelFavorite addFavoriteNovel(NovelFavorite novelFavorite){
        return novelFavoriteRepository.save(novelFavorite);
    }

    public void removeFavoriteNovel(int novelFavoriteId){
        novelFavoriteRepository.deleteById(novelFavoriteId);
    }

    /**
     *
     * @param userId
     * @param novelId
     * @return True if it is favorited, otherwise False
     */
    public boolean isFavoritedNovel(int userId, int novelId){
        Set<NovelFavorite> favoriteNovels = novelFavoriteRepository.findNoveFavoritelByUserId(userId);
        List<NovelFavorite> result = favoriteNovels.stream().filter(novelFavorite -> novelFavorite.getNovelId() == novelId).toList();
        return !result.isEmpty();
    }
}
