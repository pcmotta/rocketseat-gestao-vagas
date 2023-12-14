package br.com.attomtech.gestaovagas.modules.candidate.repositories;

import br.com.attomtech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, String> {
}
