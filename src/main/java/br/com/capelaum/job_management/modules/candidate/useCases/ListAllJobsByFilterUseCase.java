package br.com.capelaum.job_management.modules.candidate.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.capelaum.job_management.modules.company.entities.JobEntity;
import br.com.capelaum.job_management.modules.company.repositories.JobRepository;

@Service
public class ListAllJobsByFilterUseCase {

    @Autowired
    JobRepository jobRepository;

    public List<JobEntity> execute(String filter) {
        return this.jobRepository.findByDescriptionContaining(filter);
    }

}
