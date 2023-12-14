package br.com.attomtech.gestaovagas.modules.candidate.controllers;

import br.com.attomtech.gestaovagas.modules.candidate.CandidateEntity;
import br.com.attomtech.gestaovagas.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.attomtech.gestaovagas.modules.candidate.services.ApplyJobCandidateService;
import br.com.attomtech.gestaovagas.modules.candidate.services.CreateCandidateService;
import br.com.attomtech.gestaovagas.modules.candidate.services.ListAllJobsByFilterService;
import br.com.attomtech.gestaovagas.modules.candidate.services.ProfileCandidateService;
import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

    final CreateCandidateService createCandidateService;

    final ProfileCandidateService profileCandidateService;

    final ListAllJobsByFilterService listAllJobsByFilterService;
    final ApplyJobCandidateService applyJobCandidateService;

    public CandidateController(CreateCandidateService createCandidateService,
        ProfileCandidateService profileCandidateService, ListAllJobsByFilterService listAllJobsByFilterService,
        ApplyJobCandidateService applyJobCandidateService) {
        this.createCandidateService = createCandidateService;
        this.profileCandidateService = profileCandidateService;
        this.listAllJobsByFilterService = listAllJobsByFilterService;
        this.applyJobCandidateService = applyJobCandidateService;
    }

    @PostMapping
    @Operation(summary = "Cadastro de Candidato", description = "Essa função é responsável por cadastrar um candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = CandidateEntity.class))
        }),
        @ApiResponse(responseCode = "400", description = "Usuário já existe")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        try {
            var result = createCandidateService.execute(candidate);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Perfil do candidato", description = "Essa função é responsável por buscar as informações do perfil do candidato")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "User not found")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        try {
            var candidateId = request.getAttribute("candidate_id").toString();
            var result = profileCandidateService.execute(candidateId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listagem de vagas disponíveis para o candidato",
        description = "Essa função é responsável por listar todas as vagas disponíveis, baseada no filtro")
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
        })
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> findJobByFilter(@RequestParam String filter) {
        try {
            var result = listAllJobsByFilterService.execute(filter);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Inscrição do candidato para uma vaga",
        description = "Essa função é responsável por realizar a inscrição do candidato em uma vaga.")
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody String jobId) {
        try {
            var candidateId = request.getAttribute("candidate_id");
            var result = applyJobCandidateService.execute(candidateId.toString(), jobId);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
