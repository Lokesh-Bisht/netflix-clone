package dev.lokeshbisht.GenreService.controller;

import dev.lokeshbisht.GenreService.enums.ErrorCode;
import dev.lokeshbisht.GenreService.dto.ErrorResponseDto;
import dev.lokeshbisht.GenreService.exceptions.GenreNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.UUID;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GenreNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleGenreNotFoundException(GenreNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(ErrorCode.GENRE_NOT_FOUND, ex.getMessage());
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
