package dev.lokeshbisht.GenreService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MetadataDto {

    private String error;

    private String message;

    private String took;
}
