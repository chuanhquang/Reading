package com.example.quangca.controllers.api;

import com.example.quangca.dto.GenreDto;
import com.example.quangca.exception.AppException;
import com.example.quangca.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/genre")
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<GenreDto>> getGenres() {
        List<GenreDto> genres = genreService.getGenres();
        if (genres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/{genre_id}")
    public ResponseEntity<GenreDto> getGenre(@PathVariable("genre_id") int genreId) {
        GenreDto genre = genreService.getGenre(genreId);
        if (genre == null) {
            throw new AppException(404, "Genre Not Found!");
        }
        return ResponseEntity.ok(genre);
    }

    @PostMapping()
    public ResponseEntity<GenreDto> addGenre(@RequestBody GenreDto genre) {
        GenreDto addedGenre = genreService.addGenre(genre);
        if (addedGenre == null) {
            throw new AppException(404, "Cannot add genre");
        }
        return ResponseEntity.ok(addedGenre);
    }
}
