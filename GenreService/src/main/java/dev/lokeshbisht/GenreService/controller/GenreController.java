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

    @PutMapping("/genre/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponseDto<Genre> updateGenre(@PathVariable Long genreId, @RequestBody GenreRequestDto genreRequestDto) {
        return genreService.updateGenre(genreId, genreRequestDto);
    }
}
