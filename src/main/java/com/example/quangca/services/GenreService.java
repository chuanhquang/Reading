package com.example.quangca.services;

import com.example.quangca.dto.GenreDto;
import com.example.quangca.models.Genre;
import com.example.quangca.repositories.GenreRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<GenreDto> getGenres(){
        List<Genre> genres = genreRepository.findAll();
        return genres
                .stream()
                .map(genre -> modelMapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    public GenreDto getGenre(int genreId){
        Optional<Genre> genre = genreRepository.findById(genreId);
        return genre.map(value -> modelMapper.map(value, GenreDto.class)).orElse(null);
    }

    public GenreDto addGenre(GenreDto genre){
        if(genre.getName() == null){
            return null;
        }
        Genre genreFromDb = genreRepository.findGenreByName(genre.getName());
        if(genreFromDb != null){
            return null;
        }
        return modelMapper.map(genreRepository.saveAndFlush(modelMapper.map(genre, Genre.class)), GenreDto.class);
    }
}
