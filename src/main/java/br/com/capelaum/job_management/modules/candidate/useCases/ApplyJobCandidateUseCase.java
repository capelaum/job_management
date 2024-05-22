package br.com.capelaum.job_management.modules.candidate.useCases;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capelaum.job_management.exceptions.JobNotFoundException;
import br.com.capelaum.job_management.exceptions.UserNotFoundException;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import br.com.capelaum.job_management.modules.company.repositories.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    public void execute(UUID candidateId, UUID jobId) {
        candidateRepository.findById(candidateId)
                .orElseThrow(() -> {
                    throw new UserNotFoundException();
                });

        jobRepository.findById(jobId)
                .orElseThrow(() -> {
                    throw new JobNotFoundException();
                });

        // Candidatura
    }
}
