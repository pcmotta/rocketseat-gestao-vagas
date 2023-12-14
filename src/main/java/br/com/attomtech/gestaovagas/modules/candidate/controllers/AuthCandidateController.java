package br.com.attomtech.gestaovagas.modules.candidate.controllers;

import br.com.attomtech.gestaovagas.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.attomtech.gestaovagas.modules.candidate.services.AuthCandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/candidate")
public class AuthCandidateController {

    @Autowired
    AuthCandidateService authCandidateService;

    @PostMapping("/auth")
    public ResponseEntity<Object> auth(@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var token = authCandidateService.execute(authCandidateRequestDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
