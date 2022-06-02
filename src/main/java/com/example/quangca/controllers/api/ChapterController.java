package com.example.quangca.controllers.api;

import com.example.quangca.dto.ChapterDto;
import com.example.quangca.exception.AppException;
import com.example.quangca.services.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/chapter")
public class ChapterController {

    private final ChapterService chapterService;

    @Autowired
    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @GetMapping("/{novel_id}")
    public ResponseEntity<List<ChapterDto>> getChapters(@PathVariable("novel_id") int chapterId) {

        List<ChapterDto> chapters = chapterService.getChapters(chapterId);
        if (chapters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/c{chapter_id}")
    public ResponseEntity<ChapterDto> getChapter(@PathVariable("chapter_id") int chapterId) {
        ChapterDto chapter = chapterService.getChapter(chapterId);
        if (chapter == null) {
            throw new AppException(404, "Chapter Not Found!");
        }
        return ResponseEntity.ok(chapter);
    }
}
