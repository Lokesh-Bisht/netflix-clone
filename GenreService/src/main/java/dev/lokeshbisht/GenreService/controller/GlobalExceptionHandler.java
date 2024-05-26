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

import dev.lokeshbisht.GenreService.enums.ErrorCode;
import dev.lokeshbisht.GenreService.dto.ErrorResponseDto;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
import dev.lokeshbisht.GenreService.exceptions.InvalidResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.UUID;

import static dev.lokeshbisht.GenreService.constants.Errors.INVALID_RESOURCE_MESSAGE;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGenreNotFoundException(GenreNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.GENRE_NOT_FOUND, ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException: {}", ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.INVALID_RESOURCE, INVALID_RESOURCE_MESSAGE);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.INVALID_RESOURCE, INVALID_RESOURCE_MESSAGE);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleAllExceptions(Exception ex) {
        UUID uuid = UUID.randomUUID();
        String message = String.format("Unhandled exception, logged against error id: %s", uuid);
        log.error("Exception: {} {}", message, ex.getClass().getName(), ex);
        log.error(ex.getMessage(), ex);
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR, message);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
