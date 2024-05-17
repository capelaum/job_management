package br.com.capelaum.job_management.modules.candidate.controllers;

import br.com.capelaum.job_management.exceptions.UserFoundException;
import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import br.com.capelaum.job_management.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.capelaum.job_management.modules.candidate.useCases.ProfileCandidateUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

	@Autowired
	private CreateCandidateUseCase createCandidateUseCase;

	@Autowired
	private ProfileCandidateUseCase profileCandidateUseCase;

	@PostMapping("/")
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
	public ResponseEntity<Object> get(HttpServletRequest request) {
		var candidateId = request.getAttribute("candidate_id");

		try {
			var profile = this.profileCandidateUseCase.execute(UUID.fromString(candidateId.toString()));
			return ResponseEntity.ok(profile);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}
