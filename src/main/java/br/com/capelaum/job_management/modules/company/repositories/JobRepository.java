package br.com.capelaum.job_management.modules.company.repositories;

import br.com.capelaum.job_management.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
}
