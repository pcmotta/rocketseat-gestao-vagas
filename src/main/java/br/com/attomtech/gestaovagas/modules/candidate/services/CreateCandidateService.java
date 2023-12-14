package br.com.attomtech.gestaovagas.modules.candidate.services;

import br.com.attomtech.gestaovagas.exceptions.UserAlreadyExistsException;
import br.com.attomtech.gestaovagas.modules.candidate.CandidateEntity;
import br.com.attomtech.gestaovagas.modules.candidate.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateService {

    @Autowired
    CandidateRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public CandidateEntity execute(CandidateEntity candidate) {
        repository.findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
            .ifPresent(usuario -> {
                throw new UserAlreadyExistsException();
            });

        candidate.setPassword(encoder.encode(candidate.getPassword()));

        return repository.save(candidate);
    }
}
