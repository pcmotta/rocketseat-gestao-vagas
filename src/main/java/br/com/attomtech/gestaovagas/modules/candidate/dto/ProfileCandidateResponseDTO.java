package br.com.attomtech.gestaovagas.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProfileCandidateResponseDTO(

    String id,

    @Schema(example = "maria")
    String username,

    @Schema(example = "maria@gmail.com")
    String email,

    @Schema(example = "Desenvolvedora Java")
    String description,

    @Schema(example = "Maria de Souza")
    String name) {
}
