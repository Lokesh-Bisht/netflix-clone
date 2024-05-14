package dev.lokeshbisht.GenreService.service.impl;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.dto.MetadataDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.repository.GenreRepository;
import dev.lokeshbisht.GenreService.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    private static final Logger logger = LoggerFactory.getLogger(GenreServiceImpl.class);

    private ApiResponseDto<Genre> mapToApiResponseDto(Genre genre, long startTime) {
        MetadataDto metadataDto = MetadataDto.builder()
            .message("OK")
            .took(System.currentTimeMillis() - startTime + " ms")
            .build();
        return new ApiResponseDto<>(genre, metadataDto);
    }

    @Override
    public ApiResponseDto<Genre> createGenre(GenreRequestDto genreRequestDto) {
        long startTime = System.currentTimeMillis();
        logger.info("Create new genre: {}", genreRequestDto.toString());
        Genre genre = new Genre();
        genre.setName(genreRequestDto.getName());
        genre.setCreatedBy(genreRequestDto.getCreatedBy());
        genre.setCreatedAt(new Date());
        return mapToApiResponseDto(genreRepository.save(genre), startTime);
    }
}
