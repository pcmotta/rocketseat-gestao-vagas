package br.com.attomtech.gestaovagas.modules.company.dto;

import lombok.Builder;

@Builder
public record AuthCompanyResponseDTO(String accessToken, Long expiresIn) {
}
