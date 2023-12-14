package br.com.attomtech.gestaovagas.modules.company.services;

import br.com.attomtech.gestaovagas.modules.company.dto.AuthCompanyDTO;
import br.com.attomtech.gestaovagas.modules.company.dto.AuthCompanyResponseDTO;
import br.com.attomtech.gestaovagas.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyService {

    @Value("${security.token.secret}")
    String secretKey;

    @Autowired
    CompanyRepository repository;

    @Autowired
    PasswordEncoder encoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        var company = repository.findByUsername(authCompanyDTO.username())
            .orElseThrow(() -> new UsernameNotFoundException("Company not found"));

        var passwordMathces = encoder.matches(authCompanyDTO.password(), company.getPassword());

        if (!passwordMathces) {
            throw new AuthenticationException();
        }

        var expiresIn = Instant.now().plus(Duration.ofHours(2));
        var token = JWT.create()
            .withIssuer("javagas")
            .withExpiresAt(expiresIn)
            .withSubject(company.getId())
            .withClaim("roles", List.of(Roles.COMPANY))
            .sign(Algorithm.HMAC256(secretKey));

        return AuthCompanyResponseDTO.builder()
            .accessToken(token)
            .expiresIn(expiresIn.toEpochMilli())
            .build();
    }
}
