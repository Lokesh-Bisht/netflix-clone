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

package dev.lokeshbisht.GenreService.service.impl;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.dto.MetadataDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
import dev.lokeshbisht.GenreService.repository.GenreRepository;
import dev.lokeshbisht.GenreService.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

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

    @Override
    public ApiResponseDto<Genre> updateGenre(Long genreId, GenreRequestDto genreRequestDto) {
        long startTime = System.currentTimeMillis();
        logger.info("Update genre: {}", genreId);
        Optional<Genre> genre = genreRepository.findById(genreId);
        if (genre.isEmpty()) {
            logger.info("Genre with id: {} doesn't exist.", genreId);
            throw new GenreNotFoundException("Genre not found.");
        }
        genre.get().setName(genreRequestDto.getName());
        genre.get().setUpdatedBy(genreRequestDto.getUpdatedBy());
        genre.get().setUpdatedAt(new Date());
        return mapToApiResponseDto(genreRepository.save(genre.get()), startTime);
    }
}
