package com.example.quangca.services;

import com.example.quangca.dto.AuthorDto;
import com.example.quangca.models.Author;
import com.example.quangca.repositories.AuthorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<AuthorDto> getAuthors(){
        List<Author> authors = authorRepository.findAll();
        return authors
                .stream()
                .map(novel -> modelMapper.map(novel, AuthorDto.class))
                .collect(Collectors.toList());
    }

    public AuthorDto getAuthor(int authorId) {
        Optional<Author> novel = authorRepository.findById(authorId);
        return novel.map(value -> modelMapper.map(value, AuthorDto.class)).orElse(null);
    }
}
