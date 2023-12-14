package br.com.attomtech.gestaovagas.modules.candidate.services;

import br.com.attomtech.gestaovagas.exceptions.UserNotFoundException;
import br.com.attomtech.gestaovagas.modules.candidate.CandidateRepository;
import br.com.attomtech.gestaovagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class ProfileCandidateService {

    final CandidateRepository repository;

    public ProfileCandidateService(CandidateRepository repository) {
        this.repository = repository;
    }

    public ProfileCandidateResponseDTO execute(String id) {
        var candidate = repository.findById(id)
            .orElseThrow(UserNotFoundException::new);

        return ProfileCandidateResponseDTO.builder()
            .id(candidate.getId())
            .name(candidate.getName())
            .username(candidate.getUsername())
            .description(candidate.getDescription())
            .email(candidate.getEmail())
            .build();
    }
}
