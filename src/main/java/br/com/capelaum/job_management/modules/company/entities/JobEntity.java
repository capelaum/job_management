package br.com.capelaum.job_management.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "jobs")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotBlank(message = "Descrição é obrigatória")
	private String description;

	private String benefits;
	private String level;

	@ManyToOne()
	@JoinColumn(name = "company_id", insertable = false, updatable = false)
	private CompanyEntity companyEntity;

	@Column(name = "company_id", nullable = false)
	private UUID companyId;

	@CreationTimestamp
	private LocalDateTime createdAt;
}
