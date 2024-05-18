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

package dev.lokeshbisht.GenreService.service;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
import dev.lokeshbisht.GenreService.repository.GenreRepository;
import dev.lokeshbisht.GenreService.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

    private static final List<Genre> genreList = new ArrayList<>();

    @BeforeAll
    static void generateGenreList() {

        Genre genre = new Genre(8L, "Fantasy", "Inu", null, "Uttara", null);
        Genre genre2 = new Genre(11L, "Documentary", "Pururava ", null, "Nahush", null);
        Genre genre3 = new Genre(14L, "Romance", "Ayu", null, "Ira", null);
        genreList.add(genre);
        genreList.add(genre2);
        genreList.add(genre3);
    }

    @Test
    void createGenreTestFromService() {

        // Mock repository response
        when(genreRepository.save(Mockito.any())).thenReturn(new Genre(1L, "Action", "lokeshbisht", new Date(), null, null));

        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Action");
        genreRequestDto.setCreatedBy("lokeshbisht.dev.in");

        ApiResponseDto<Genre> result = genreService.createGenre(genreRequestDto);


        assertNotNull(result.getData());
        assertEquals("Action", result.getData().getName());
    }

    @Test
    void testUpdateGenre() {
        when(genreRepository.findById(2L)).thenReturn(Optional.of(new Genre(2L, "Action", "lokesh bisht", new Date(), null, null)));

        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Romantic comedy");
        genreRequestDto.setUpdatedBy("Aruni");

        Genre updatedGenreEntity = new Genre(2L, genreRequestDto.getName(), "lokesh bisht", new Date(), genreRequestDto.getUpdatedBy(), new Date());
        when(genreRepository.save(genreRepository.findById(2L).get())).thenReturn(updatedGenreEntity);

        ApiResponseDto<Genre> result = genreService.updateGenre(2L, genreRequestDto);
        assertNotNull(result.getData());
        assertEquals(2, result.getData().getId());
        assertEquals("Romantic comedy", result.getData().getName());
        assertEquals("lokesh bisht", result.getData().getCreatedBy());
        assertEquals("Aruni", result.getData().getUpdatedBy());
        assertEquals("OK", result.getMetadataDto().getMessage());
    }

    @Test
    void testUpdateGenreThatDoesNotExist() {
        when(genreRepository.findById(545L)).thenThrow(new GenreNotFoundException("Genre not found."));

        String errorMessage = "";
        try {
            genreService.updateGenre(545L, new GenreRequestDto());
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }
        assertThrows(GenreNotFoundException.class, () -> genreService.updateGenre(545L, new GenreRequestDto()));
        assertEquals("Genre not found.", errorMessage);
    }

    @Test
    void testGetGenre() {
        Genre genre = genreList.get(1);
        when(genreRepository.findById(genre.getId())).thenReturn(Optional.of(genre));

        ApiResponseDto<Genre> result = genreService.getGenreById(genre.getId());
        Genre retrievedEntity = result.getData();

        assertNotNull(retrievedEntity);
        assertEquals(genre.getId(), retrievedEntity.getId());
        assertEquals(genreList.get(1).getName(), retrievedEntity.getName());
        assertEquals(genreList.get(1).getCreatedBy(), retrievedEntity.getCreatedBy());
        assertEquals(genreList.get(1).getUpdatedBy(), retrievedEntity.getUpdatedBy());

        assertNotNull(result.getMetadataDto());
        assertEquals("OK", result.getMetadataDto().getMessage());
    }

    @Test
    void testGetInvalidGenre() {
        when(genreRepository.findById(545L)).thenThrow(new GenreNotFoundException("Genre not found."));

        String errorMessage = "";
        try {
            genreService.getGenreById(545L);
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }
        assertThrows(GenreNotFoundException.class, () -> genreService.getGenreById(545L));
        assertEquals("Genre not found.", errorMessage);
    }
}
