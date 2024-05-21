package br.com.capelaum.job_management.modules.candidate.dto;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
	private UUID id;

	@Schema(example = "ze")
	private String username;

	@Schema(example = "ze@email.com")
	private String email;

	@Schema(example = "Zé da Silva")
	private String name;

	@Schema(example = "Dev Java")
	private String description;
}
