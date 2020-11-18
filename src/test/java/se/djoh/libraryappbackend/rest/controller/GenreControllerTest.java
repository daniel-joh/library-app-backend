package se.djoh.libraryappbackend.rest.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import se.djoh.libraryappbackend.service.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class GenreControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    private GenreService genreService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void gettingGenresSuccessfullyWithAuthTest() throws Exception {
        List<String> genres = new ArrayList<>();
        genres.add("genre1");
        genres.add("genre2");
        when(genreService.getGenres()).thenReturn(genres);

        MvcResult storyResult = mockMvc.perform(get("/api/genres")
                .with(httpBasic("user", "user")))
                .andExpect(status().isOk())
                .andReturn();

        List<String> returnedGenres = OBJECT_MAPPER.readValue(storyResult.getResponse().getContentAsString(),
                new TypeReference<List<String>>() {
                });

        assertEquals(2, returnedGenres.size());
    }

    @Test
    public void gettingGenresWhenNoGenresExistWithAuthShouldResultIn404StatusTest() throws Exception {
        when(genreService.getGenres()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/api/genres")
                .with(httpBasic("user", "user")))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void gettingGenresWithNoAuthShouldResultIn401StatusTest() throws Exception {
        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

}
