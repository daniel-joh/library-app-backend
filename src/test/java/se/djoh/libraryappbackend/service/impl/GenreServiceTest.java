package se.djoh.libraryappbackend.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.domain.Genre;
import se.djoh.libraryappbackend.repository.GenreRepository;
import se.djoh.libraryappbackend.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class GenreServiceTest {
    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreService genreService = new GenreServiceImpl();

    @Test
    public void gettingGenresSuccessfulTest() {
        List<Genre> genres = new ArrayList<>();
        genres.add(new Genre());
        genres.add(new Genre());
        when(genreRepository.findAll()).thenReturn(genres);

        List<String> returnedGenres = genreService.getGenres();

        assertEquals(2, returnedGenres.size());
    }

    @Test
    public void gettingGenresWithNoFoundGenresTest() {
        when(genreRepository.findAll()).thenReturn(new ArrayList<>());

        List<String> returnedGenres = genreService.getGenres();

        assertEquals(0, returnedGenres.size());
    }
}
