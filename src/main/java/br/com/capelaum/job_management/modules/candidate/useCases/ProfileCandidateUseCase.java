package br.com.capelaum.job_management.modules.candidate.useCases;

import br.com.capelaum.job_management.exceptions.UserNotFoundException;
import br.com.capelaum.job_management.modules.candidate.dto.ProfileCandidateResponseDTO;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID candidateId) {
        var candidate = this.candidateRepository.findById(candidateId)
                .orElseThrow(UserNotFoundException::new);

        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .username(candidate.getUsername())
                .email(candidate.getEmail())
                .name(candidate.getName())
                .description(candidate.getDescription())
                .build();

        return candidateDTO;
    }

}
