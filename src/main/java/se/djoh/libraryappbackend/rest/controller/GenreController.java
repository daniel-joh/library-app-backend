package se.djoh.libraryappbackend.rest.controller;

import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.djoh.libraryappbackend.rest.exception.ResourceNotFoundException;
import se.djoh.libraryappbackend.service.GenreService;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
@Log
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping(path = "/genres")
    public List<String> getAllGenres() {
        List<String> genres = genreService.getGenres();

        if (genres.isEmpty()) {
            throw new ResourceNotFoundException("No genres found!");
        }
        return genres;
    }
}
