package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import se.djoh.libraryappbackend.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Test
    public void gettingGenresSuccessfullyTest() throws Exception {
        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");
        when(genreService.getGenres()).thenReturn(genres);

        MvcResult storyResult = mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andReturn();

        List<String> returnedGenres = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(),
                new TypeReference<List<String>>(){});

        assertEquals(2, returnedGenres.size());
    }

    @Test
    public void gettingGenresWhenNoGenresExistShouldResultIn404StatusTest() throws Exception {
        when(genreService.getGenres()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isNotFound())
                .andDo(print())
                .andReturn();
    }

}
