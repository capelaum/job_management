package br.com.capelaum.job_management.modules.candidate.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.capelaum.job_management.exceptions.UserFoundException;
import br.com.capelaum.job_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import br.com.capelaum.job_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.capelaum.job_management.modules.candidate.useCases.ListAllJobsByFilterUseCase;
import br.com.capelaum.job_management.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.capelaum.job_management.modules.company.entities.JobEntity;
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
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Informações do candidato")
public class CandidateController {

	@Autowired
	private CreateCandidateUseCase createCandidateUseCase;

	@Autowired
	private ProfileCandidateUseCase profileCandidateUseCase;

	@Autowired
	private ListAllJobsByFilterUseCase listAllJobsByFilterUseCase;

	@PostMapping("/")
	@Operation(summary = "Cadastro de candidato", description = "Cadastro de novo candidato")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = CandidateEntity.class))
			}),

			@ApiResponse(responseCode = "400", description = "Usuário já existe"),
			@ApiResponse(responseCode = "500", description = "Ocorreu um erro inesperado")
	})
	public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity) {
		try {
			CandidateEntity createdCandidate = this.createCandidateUseCase.execute(candidateEntity);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdCandidate);
		} catch (UserFoundException | ValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado");
		}
	}

	@GetMapping("/")
	@PreAuthorize("hasRole('CANDIDATE')")
	@Operation(summary = "Perfil do candidato", description = "Dados do candidato")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
			}),

			@ApiResponse(responseCode = "400", description = "User not found")
	})
	@SecurityRequirement(name = "jwt_auth")
	public ResponseEntity<Object> get(HttpServletRequest request) {
		var candidateId = request.getAttribute("candidate_id");

		try {
			var profile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
			return ResponseEntity.ok(profile);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/job")
	@PreAuthorize("hasRole('CANDIDATE')")
	@Operation(summary = "Listagem de vagas disponíveis para o candidato", description = "Lista todas vagas disponíveis, aceitando filtros aplicados no campo de descrição")
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
			})
	})
	@SecurityRequirement(name = "jwt_auth")
	public List<JobEntity> findJobByFilter(@RequestParam String filter) {
		return listAllJobsByFilterUseCase.execute(filter);
	}
}
