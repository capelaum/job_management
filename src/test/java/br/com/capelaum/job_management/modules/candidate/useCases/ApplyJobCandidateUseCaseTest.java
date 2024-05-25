package br.com.capelaum.job_management.modules.candidate.useCases;

import br.com.capelaum.job_management.exceptions.JobNotFoundException;
import br.com.capelaum.job_management.exceptions.UserNotFoundException;
import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import br.com.capelaum.job_management.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

	@InjectMocks
	private ApplyJobCandidateUseCase applyJobCandidateUseCase;

	@Mock
	private CandidateRepository candidateRepository;

	@Mock
	private JobRepository jobRepository;

	@Test
	@DisplayName("Should not be able to apply to job with candidate not found")
	public void should_not_be_able_to_apply_to_job_with_candidate_id_not_found() {
		try {
			applyJobCandidateUseCase.execute(null, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
		}
	}

	@Test
	@DisplayName("Should not be able to apply to job not found")
	public void should_not_be_able_to_apply_to_job_not_found() {
		var idCandidate = UUID.randomUUID();

		var candidate = new CandidateEntity();
		candidate.setId(idCandidate);

		when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

		try {
			applyJobCandidateUseCase.execute(idCandidate, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(JobNotFoundException.class);
		}
	}

}
