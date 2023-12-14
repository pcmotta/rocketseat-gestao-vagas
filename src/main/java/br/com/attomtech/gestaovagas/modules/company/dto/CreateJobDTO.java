package br.com.attomtech.gestaovagas.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record CreateJobDTO(
    @Schema(example = "Vaga para desenvolvedora júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    String description,

    @Schema(example = "GymPass, Plano de Saúde", requiredMode = Schema.RequiredMode.REQUIRED)
    String benefits,

    @Schema(example = "JUNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
    String level) {
}
