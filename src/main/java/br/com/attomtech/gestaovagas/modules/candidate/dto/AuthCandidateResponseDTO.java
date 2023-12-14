package br.com.attomtech.gestaovagas.modules.candidate.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record AuthCandidateResponseDTO(String acessToken, Long expiresIn) {
}
