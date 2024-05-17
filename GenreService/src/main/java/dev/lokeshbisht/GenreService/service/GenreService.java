package dev.lokeshbisht.GenreService.service;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.entity.Genre;

public interface GenreService {

    ApiResponseDto<Genre> createGenre(GenreRequestDto genreRequestDto);
    ApiResponseDto<Genre> updateGenre(Long genreId, GenreRequestDto genreRequestDto);
}
