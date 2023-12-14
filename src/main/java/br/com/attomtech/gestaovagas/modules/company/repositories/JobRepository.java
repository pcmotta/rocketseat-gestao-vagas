package br.com.attomtech.gestaovagas.modules.company.repositories;

import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, String> {
    List<JobEntity> findByDescriptionContainingIgnoreCase(String description);
}
