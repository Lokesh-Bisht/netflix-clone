package dev.lokeshbisht.GenreService.exceptions;

import java.io.Serial;

public class GenreNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public GenreNotFoundException(String message) {
        super(message);
    }
}
