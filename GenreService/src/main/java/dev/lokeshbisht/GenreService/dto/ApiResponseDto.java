package dev.lokeshbisht.GenreService.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto<T> {

    private T data;

    @JsonProperty("metadata")
    private MetadataDto metadataDto;
}
