package br.com.attomtech.gestaovagas.modules.candidate.services;

import br.com.attomtech.gestaovagas.modules.candidate.CandidateRepository;
import br.com.attomtech.gestaovagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.attomtech.gestaovagas.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.attomtech.gestaovagas.security.Roles;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCandidateService {

    @Value("${security.token.secret.candidate}")
    String secretKey;

    @Autowired
    CandidateRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws
        AuthenticationException {
        var candidate = repository.findByUsername(authCandidateRequestDTO.username())
            .orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect!"));

        var passwordMatches = encoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new AuthenticationException();
        }

        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
            .withIssuer("javagas")
            .withSubject(candidate.getId())
            .withClaim("roles", List.of(Roles.CANDIDATE))
            .withExpiresAt(expiresIn)
            .sign(Algorithm.HMAC256(secretKey));

        return AuthCandidateResponseDTO.builder()
            .acessToken(token)
            .expiresIn(expiresIn.toEpochMilli())
            .build();
    }
}
