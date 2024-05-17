package dev.lokeshbisht.GenreService.service;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
import dev.lokeshbisht.GenreService.repository.GenreRepository;
import dev.lokeshbisht.GenreService.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class GenreServiceTest {

    @Mock
    private GenreRepository genreRepository;

    @InjectMocks
    private GenreServiceImpl genreService;

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
            ApiResponseDto<Genre> ex = genreService.updateGenre(545L, new GenreRequestDto());
        } catch (Exception ex) {
            errorMessage = ex.getMessage();
        }
        assertEquals("Genre not found.", errorMessage);
    }
}
