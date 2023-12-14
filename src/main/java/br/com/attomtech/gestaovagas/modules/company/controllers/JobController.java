package br.com.attomtech.gestaovagas.modules.company.controllers;

import br.com.attomtech.gestaovagas.modules.company.dto.CreateJobDTO;
import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import br.com.attomtech.gestaovagas.modules.company.services.CreateJobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/job")
public class JobController {

    @Autowired
    CreateJobService createJobService;

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    @Tag(name = "Vagas", description = "Informações de vagas")
    @Operation(summary = "Cadastro de Vagas", description = "Essa função é responsável por cadastrar as vagas dentro da empresa")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = JobEntity.class))
        })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreateJobDTO dto, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var job = JobEntity.builder()
                .benefits(dto.benefits())
                .description(dto.description())
                .level(dto.level())
                .companyId(companyId.toString())
                .build();

            var result = createJobService.execute(job);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
