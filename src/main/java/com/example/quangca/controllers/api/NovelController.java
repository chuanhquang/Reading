package com.example.quangca.controllers.api;

import com.example.quangca.dto.NovelDto;
import com.example.quangca.exception.AppException;
import com.example.quangca.services.NovelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/novel")
//@RequiredArgsConstructor
//@AllArgsConstructor
public class NovelController {
    private final NovelService novelService;

    @Autowired
    public NovelController(NovelService novelService) {
        this.novelService = novelService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<NovelDto>> getNovels() {
        List<NovelDto> novels = novelService.getNovels(null);
        if (novels.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(novels);
    }

    @GetMapping("/{novel_id}")
    public ResponseEntity<NovelDto> getNovel(@PathVariable("novel_id") int novelId) {
        NovelDto novel = novelService.getNovel(novelId);
        if (novel == null) {
            throw new AppException(404, "Novel Not Found!");
        }
        return ResponseEntity.ok(novel);
    }

    @PostMapping()
    public ResponseEntity<NovelDto> addNovel(@RequestBody NovelDto novel) {
        NovelDto addedNovel = novelService.addNovel(novel);
        if (addedNovel == null) {
            throw new AppException(404, "Cannot add novel");
        }
        return ResponseEntity.ok(addedNovel);
    }

    @PutMapping("/{novel_id}")
    public ResponseEntity<NovelDto> updateNovel(@PathVariable("novel_id") int novelId, @RequestBody NovelDto novel) {
        NovelDto addedNovel = novelService.updateNovel(novelId, novel);
        if (addedNovel == null) {
            throw new AppException(404, "Novel Not Found!");
        }
        return ResponseEntity.ok(addedNovel);
    }

    @DeleteMapping("/{novel_id}")
    public ResponseEntity<NovelDto> deleteNovel(@PathVariable("novel_id") int novelId) {
        novelService.deleteNovel(novelId);
        return ResponseEntity.ok().build();
    }
}
