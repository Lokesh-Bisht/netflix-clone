package dev.lokeshbisht.GenreService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import dev.lokeshbisht.GenreService.enums.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    @JsonProperty("error_code")
    private ErrorCode errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    @JsonProperty("errors")
    private Map<String, Object> errors;

    public ErrorResponseDto(ErrorCode errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
