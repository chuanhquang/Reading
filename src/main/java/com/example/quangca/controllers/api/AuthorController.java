package com.example.quangca.controllers.api;

import com.example.quangca.dto.AuthorDto;
import com.example.quangca.exception.AppException;
import com.example.quangca.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<AuthorDto>> getAuthors() {

        List<AuthorDto> chapters = authorService.getAuthors();
        if (chapters.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(chapters);
    }

    @GetMapping("/{author_id}")
    public ResponseEntity<AuthorDto> getAuthor(@PathVariable("author_id") int authorId) {
        AuthorDto chapter = authorService.getAuthor(authorId);
        if (chapter == null) {
            throw new AppException(404, "Author Not Found!");
        }
        return ResponseEntity.ok(chapter);
    }
}
