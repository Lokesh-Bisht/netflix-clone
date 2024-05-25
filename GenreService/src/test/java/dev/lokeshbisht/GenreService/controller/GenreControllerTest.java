/*
 * Copyright (C) 2024 Lokesh Bisht
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package dev.lokeshbisht.GenreService.controller;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.dto.MetadataDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void testCreateGenre() throws Exception {
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

    @Test
    void testUpdateGenre() throws Exception {
        Genre genre = new Genre();
        genre.setId(2L);
        genre.setName("Comedy");
        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Comedy");
        genreRequestDto.setUpdatedBy("som");
        when(genreService.updateGenre(2L, genreRequestDto)).thenReturn(new ApiResponseDto<>(genre, new MetadataDto()));
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put("/v1/genre/2")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Comedy\", \"updated_by\": \"som\"}"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("data.name").value("Comedy"))
            .andReturn();
    }

    @Test
    void testUpdateGenreNotFound() throws Exception {
        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Comedy");
        genreRequestDto.setUpdatedBy("som");
        when(genreService.updateGenre(4L, genreRequestDto)).thenThrow(new GenreNotFoundException("Genre not found."));
        mockMvc.perform(MockMvcRequestBuilders.put("/v1/genre/4")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\": \"Comedy\", \"updated_by\": \"som\"}"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("error_code").value("GENRE_NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath("error_message").value("Genre not found."))
            .andReturn();
    }

    @Test
    void testGetGenre() throws Exception {
        Genre genre = Genre.builder()
            .id(34532L)
            .name("Thriller")
            .createdBy("Aditya")
            .build();
        when(genreService.getGenreById(34532L)).thenReturn(new ApiResponseDto<>(genre, new MetadataDto("", "OK", null)));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/genre/34532"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("data.id").value(34532))
            .andExpect(MockMvcResultMatchers.jsonPath("data.name").value("Thriller"))
            .andExpect(MockMvcResultMatchers.jsonPath("data.createdBy").value("Aditya"))
            .andExpect(MockMvcResultMatchers.jsonPath("metadata.message").value("OK"))
            .andReturn();
    }

    @Test
    void testGetInvalidGenre() throws Exception {
        when(genreService.getGenreById(234L)).thenThrow(new GenreNotFoundException("Genre not found."));
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/genre/234"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("error_code").value("GENRE_NOT_FOUND"))
            .andExpect(MockMvcResultMatchers.jsonPath("error_message").value("Genre not found."))
            .andReturn();
    }

    @Test
    void testGetAllGenres() throws Exception {
        List<Genre> genreList = new ArrayList<>();
        Genre genre = new Genre(8L, "Fantasy", "Inu", null, "Uttara", null);
        Genre genre2 = new Genre(11L, "Documentary", "Pururava ", null, "Nahush", null);
        Genre genre3 = new Genre(14L, "Romance", "Ayu", null, "Ira", null);
        genreList.add(genre);
        genreList.add(genre2);
        genreList.add(genre3);

        when(genreService.getAllGenres()).thenReturn(new ApiResponseDto<>(genreList, new MetadataDto("", "OK", null)));

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/genre/all"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(genreList.size()))
            .andReturn();
    }

    @Test
    void testInvalidUriRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/test"))
            .andExpect(MockMvcResultMatchers.status().isNotFound())
            .andExpect(MockMvcResultMatchers.jsonPath("error_code").value("INVALID_RESOURCE"))
            .andExpect(MockMvcResultMatchers.jsonPath("error_message").value("This page doesn't exist."))
            .andReturn();
    }
}
