package br.com.attomtech.gestaovagas.modules.company.controllers;

import br.com.attomtech.gestaovagas.modules.company.dto.AuthCompanyDTO;
import br.com.attomtech.gestaovagas.modules.company.services.AuthCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/company")
public class AuthCompanyController {

    @Autowired
    AuthCompanyService authCompanyService;

    @PostMapping("/auth")
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
        try {
            var result = authCompanyService.execute(authCompanyDTO);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
