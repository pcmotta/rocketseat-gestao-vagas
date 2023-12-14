package br.com.attomtech.gestaovagas.modules.company.services;

import br.com.attomtech.gestaovagas.exceptions.UserAlreadyExistsException;
import br.com.attomtech.gestaovagas.modules.company.entities.CompanyEntity;
import br.com.attomtech.gestaovagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyService {

    @Autowired
    CompanyRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public CompanyEntity execute(CompanyEntity company) {
        repository.findByUsernameOrEmail(company.getUsername(), company.getEmail())
            .ifPresent(user -> {
                throw new UserAlreadyExistsException();
            });

        company.setPassword(encoder.encode(company.getPassword()));

        return repository.save(company);
    }
}
