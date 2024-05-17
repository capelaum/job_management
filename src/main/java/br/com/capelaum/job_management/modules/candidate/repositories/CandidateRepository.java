package br.com.capelaum.job_management.modules.candidate.repositories;

import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {
	Optional<CandidateEntity> findByUsernameOrEmail(String username, String email);

	Optional<CandidateEntity> findByUsername(String username);
}
