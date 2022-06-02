package com.example.quangca.services;

import com.example.quangca.dto.ChapterDto;
import com.example.quangca.models.Chapter;
import com.example.quangca.repositories.ChapterRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChapterService {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ChapterDto> getChapters(int novelId) {
        List<Chapter> chapters = chapterRepository.findChaptersByNovelId(novelId);
        return chapters
                .stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDto.class))
                .collect(Collectors.toList());
    }

    public void sortChapter(List<ChapterDto> chapters) {
        Collections.sort(chapters, Comparator.comparing(ChapterDto::getNumber));
    }

    public List<ChapterDto> getAdjacentChapters(ChapterDto currentChapter, List<ChapterDto> orderedChapters) {
        ChapterDto chapter;
        chapter = orderedChapters.stream()
                .filter(c -> c.getId() == currentChapter.getId())
                .findFirst().get();
        int currentChapterIndex = orderedChapters.indexOf(chapter);
        List<ChapterDto> adjacentChapters = new ArrayList<>();
        if (currentChapterIndex == 0) {
            adjacentChapters.add(null);
        } else {
            adjacentChapters.add(orderedChapters.get(currentChapterIndex - 1));
        }
        if (currentChapterIndex == orderedChapters.size() - 1) {
            adjacentChapters.add(null);
        } else {
            adjacentChapters.add(orderedChapters.get(currentChapterIndex + 1));
        }
        return adjacentChapters;
    }

    public ChapterDto getChapter(int chapterId) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        return chapter.map(value -> modelMapper.map(value, ChapterDto.class)).orElse(null);
    }

}
