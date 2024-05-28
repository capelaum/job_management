package br.com.capelaum.job_management.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateJobDTO {

	@Schema(example = "Vaga para pessoa desenvolvedora Java Pleno", requiredMode = RequiredMode.REQUIRED)
	private String description;

	@Schema(example = "Vale Alimentação/Refeição, Plano de Saúde, Home Office", requiredMode = RequiredMode.REQUIRED)
	private String benefits;

	@Schema(example = "Pleno", requiredMode = RequiredMode.REQUIRED)
	private String level;
}
