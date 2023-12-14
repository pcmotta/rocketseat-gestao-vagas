package br.com.attomtech.gestaovagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, String> {
    Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

    Optional<CandidateEntity> findByUsername(String username);
}
