package se.djoh.libraryappbackend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.djoh.libraryappbackend.repository.GenreRepository;
import se.djoh.libraryappbackend.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Override
    public List<String> getGenres() {
        return genreRepository.findAll().stream().map(genre -> genre.getName()).collect(Collectors.toList());
    }
}
