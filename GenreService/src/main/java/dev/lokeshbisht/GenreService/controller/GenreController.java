package dev.lokeshbisht.GenreService.controller;

import dev.lokeshbisht.GenreService.dto.ApiResponseDto;
import dev.lokeshbisht.GenreService.dto.GenreRequestDto;
import dev.lokeshbisht.GenreService.entity.Genre;
import dev.lokeshbisht.GenreService.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/v1")
public class GenreController {

    @Autowired
    private GenreService genreService;

    @PostMapping("/genre")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponseDto<Genre> createGenre(@RequestBody GenreRequestDto genreRequestDto) {
        return genreService.createGenre(genreRequestDto);
    }
}
