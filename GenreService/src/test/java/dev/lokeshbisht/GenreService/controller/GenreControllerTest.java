package dev.lokeshbisht.GenreService.controller;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.dto.MetadataDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.service.GenreService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void createGenreTest() throws Exception {
        Genre genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy");
        genre.setCreatedBy("lokeshbisht.dev.in");
        genre.setCreatedAt(new Date());

        // Mock GenreService response
        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Fantasy");
        genreRequestDto.setCreatedBy("lokeshbisht.dev.in");
        when(genreService.createGenre(genreRequestDto)).thenReturn(new ApiResponseDto<>(genre, new MetadataDto()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/genre")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Fantasy\", \"created_by\": \"lokeshbisht.dev.in\"}"))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(1))
            .andExpect(MockMvcResultMatchers.jsonPath("data.name").value("Fantasy"))
            .andReturn();
    }
}