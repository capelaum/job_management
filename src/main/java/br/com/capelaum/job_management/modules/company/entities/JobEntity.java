package br.com.capelaum.job_management.modules.company.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "jobs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Schema(example = "Vaga para pessoa desenvolvedora Java Pleno")
	@NotBlank(message = "Descrição é obrigatória")
	private String description;

	@Schema(example = "Vale Alimentação/Refeição, Plano de Saúde, Home Office")
	private String benefits;

	@Schema(example = "Pleno")
	private String level;

	@ManyToOne()
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	private CompanyEntity companyEntity;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
