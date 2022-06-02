package com.example.quangca.services;

import com.example.quangca.dto.NovelDto;
import com.example.quangca.models.Novel;
import com.example.quangca.repositories.NovelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class NovelService {
    @Autowired
    private NovelRepository novelRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<NovelDto> getNovels(Integer limit){
        List<Novel> novels = Optional.ofNullable(limit)
                .map(value -> novelRepository.findAll(PageRequest.of(0,limit)).getContent())
                .orElseGet(() -> novelRepository.findAll());
        return novels
                .stream()
                .map(novel -> modelMapper.map(novel, NovelDto.class))
                .collect(Collectors.toList());
    }

    public NovelDto getNovel(int novelId) {
        Optional<Novel> novel = novelRepository.findById(novelId);
        return novel.map(value -> modelMapper.map(value, NovelDto.class)).orElse(null);
    }

    public NovelDto addNovel(NovelDto novel){
        if(novel.getName() == null){
            return null;
        }
        Novel novelFromDb = novelRepository.findNovelByName(novel.getName());
        if(novelFromDb != null){
            return null;
        }
        return modelMapper.map(novelRepository.saveAndFlush(modelMapper.map(novel, Novel.class)), NovelDto.class);
    }

    public NovelDto updateNovel(int novelId, NovelDto novel){
        if(novelId == 0){
            return null;
        }
        Novel novelFromDb = novelRepository.findById(novelId).orElse(null);
        if(novelFromDb == null){
            return null;
        }
        return modelMapper.map(novelRepository.save(modelMapper.map(novel, Novel.class)), NovelDto.class);

    }

    public Set<Novel> getFavoritedNovels(String username){
        return novelRepository.findFavoritedNovelsByUsername(username);
    }

    public List<NovelDto> getNovelsByGenre(int genreId){
        List<Novel> novels = novelRepository.findNovelsByGenreId(genreId);
        return novels
                .stream()
                .map(novel -> modelMapper.map(novel, NovelDto.class))
                .collect(Collectors.toList());
    }

    public void deleteNovel(int novelId){
        novelRepository.deleteById(novelId);
    }
}
