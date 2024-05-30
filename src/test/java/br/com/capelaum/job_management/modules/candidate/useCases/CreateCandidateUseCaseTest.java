package br.com.capelaum.job_management.modules.candidate.useCases;

import br.com.capelaum.job_management.exceptions.UserFoundException;
import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCandidateUseCaseTest {

	@InjectMocks
	private CreateCandidateUseCase createCandidateUseCase;

	@Mock
	private CandidateRepository candidateRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Test
	@DisplayName("Should not be able to create candidate with same username or email")
	public void should_not_be_able_to_create_candidate_with_same_username_or_email() {
		CandidateEntity candidate = CandidateEntity.builder()
				.username("zezinho")
				.email("ze@email.com")
				.build();

		candidateRepository.saveAndFlush(candidate);

		when(passwordEncoder.encode(candidate.getPassword())).thenReturn(candidate.getPassword());

		try {
			createCandidateUseCase.execute(candidate);
		} catch (Exception e) {
			Assertions.assertInstanceOf(UserFoundException.class, e);
		}
	}

	@Test
	public void should_be_able_to_create_candidate() {
		CandidateEntity candidate = CandidateEntity.builder()
				.id(UUID.randomUUID())
				.username("zezinho")
				.email("ze@email.com")
				.build();

		when(passwordEncoder.encode(candidate.getPassword())).thenReturn(candidate.getPassword());
		when(candidateRepository.save(candidate)).thenReturn(candidate);

		var createdCandidate = createCandidateUseCase.execute(candidate);

		assertThat(createdCandidate).hasFieldOrProperty("id");
		assertNotNull(createdCandidate.getId());
		assertEquals(createdCandidate.getUsername(), "zezinho");
		assertEquals(createdCandidate.getEmail(), "ze@email.com");
	}

}
