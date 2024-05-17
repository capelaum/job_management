package br.com.capelaum.job_management.modules.company.controllers;

import br.com.capelaum.job_management.modules.company.dto.CreateJobDTO;
import br.com.capelaum.job_management.modules.company.entities.JobEntity;
import br.com.capelaum.job_management.modules.company.useCases.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

	@Autowired
	private CreateJobUseCase createJobUseCase;

	@PostMapping("/")
	public ResponseEntity<Object> create(
			@Valid @RequestBody CreateJobDTO createJobDTO, HttpServletRequest request) {
		try {
			var companyId = request.getAttribute("company_id");

			JobEntity jobEntity = JobEntity.builder()
					.companyId(UUID.fromString(companyId.toString()))
					.benefits(createJobDTO.getBenefits())
					.description(createJobDTO.getDescription())
					.level(createJobDTO.getLevel())
					.build();

			JobEntity createdJob = this.createJobUseCase.execute(jobEntity);

			return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
		} catch (ValidationException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Ocorreu um erro inesperado");
		}
	}
}
