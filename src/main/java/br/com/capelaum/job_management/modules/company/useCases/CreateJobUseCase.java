package br.com.capelaum.job_management.modules.company.useCases;

import br.com.capelaum.job_management.exceptions.CompanyNotFoundException;
import br.com.capelaum.job_management.modules.company.entities.JobEntity;
import br.com.capelaum.job_management.modules.company.repositories.CompanyRepository;
import br.com.capelaum.job_management.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public JobEntity execute(JobEntity jobEntity) {
        companyRepository.findById(jobEntity.getCompanyId()).orElseThrow(() -> {
            throw new CompanyNotFoundException();
        });

        return this.jobRepository.save(jobEntity);
    }
}
