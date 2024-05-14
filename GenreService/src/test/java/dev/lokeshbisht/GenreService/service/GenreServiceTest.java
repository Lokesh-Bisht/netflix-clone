package dev.lokeshbisht.GenreService.service;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.repository.GenreRepository;
import dev.lokeshbisht.GenreService.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

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
        System.out.println(genreRepository);
        when(genreRepository.save(Mockito.any())).thenReturn(new Genre(1L, "Action", "lokeshbisht", new Date(), null, null));

        GenreRequestDto genreRequestDto = new GenreRequestDto();
        genreRequestDto.setName("Action");
        genreRequestDto.setCreatedBy("lokeshbisht.dev.in");

        ApiResponseDto<Genre> result = genreService.createGenre(genreRequestDto);


        assertNotNull(result.getData());
        assertEquals("Action", result.getData().getName());
    }
}
