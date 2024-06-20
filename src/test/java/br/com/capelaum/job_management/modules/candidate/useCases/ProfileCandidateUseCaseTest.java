package br.com.capelaum.job_management.modules.candidate.useCases;

import br.com.capelaum.job_management.exceptions.UserNotFoundException;
import br.com.capelaum.job_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.capelaum.job_management.modules.candidate.entities.CandidateEntity;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileCandidateUseCaseTest {

	@InjectMocks
	private ProfileCandidateUseCase profileCandidateUseCase;

	@Mock
	private CandidateRepository candidateRepository;

	private UUID candidateId;
	private CandidateEntity candidate;

	@BeforeEach
	public void setUp() {
		candidateId = UUID.randomUUID();
		candidate = createMockCandidate(candidateId);
	}

	private CandidateEntity createMockCandidate(UUID candidateId) {
		CandidateEntity candidate = new CandidateEntity();
		candidate.setId(candidateId);
		candidate.setUsername("zezinho");
		candidate.setEmail("ze@example.com");
		candidate.setName("Zé da Silva");
		candidate.setDescription("A software developer.");
		return candidate;
	}

	@Test
	@DisplayName("Should be able to get candidate profile")
	public void should_be_able_to_get_candidate_profile() {
		when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

		ProfileCandidateResponseDTO response = profileCandidateUseCase.execute(candidateId);

		assertNotNull(response);
		assertEquals(candidateId, response.getId());
		assertEquals("zezinho", response.getUsername());
		assertEquals("ze@example.com", response.getEmail());
		assertEquals("Zé da Silva", response.getName());
		assertEquals("A software developer.", response.getDescription());
	}

	@Test
	@DisplayName("Should not be able to get profile with candidate not found")
	public void should_not_be_able_to_get_profile_with_candidate_not_found() {
		when(candidateRepository.findById(candidateId)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> {
			profileCandidateUseCase.execute(candidateId);
		});
	}

	@Test
	@DisplayName("Should be able to find candidate by ID with the Candidate Repository")
	public void should_bet_able_to_find_candidate_by_id_with_candidate_repository() {
		when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(candidate));

		profileCandidateUseCase.execute(candidateId);

		Mockito.verify(candidateRepository).findById(candidateId);
	}

}
