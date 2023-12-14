package br.com.attomtech.gestaovagas.modules.company.repositories;

import br.com.attomtech.gestaovagas.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, String> {
    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);
    Optional<CompanyEntity> findByUsername(String username);
}
