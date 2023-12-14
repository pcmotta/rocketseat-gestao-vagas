package br.com.attomtech.gestaovagas.modules.company.services;

import br.com.attomtech.gestaovagas.exceptions.CompanyNotFoundException;
import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import br.com.attomtech.gestaovagas.modules.company.repositories.CompanyRepository;
import br.com.attomtech.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobService {

    final JobRepository repository;
    final CompanyRepository companyRepository;

    public CreateJobService(CompanyRepository companyRepository, JobRepository repository) {
        this.companyRepository = companyRepository;
        this.repository = repository;
    }

    public JobEntity execute(JobEntity job) {
        companyRepository.findById(job.getCompanyId())
            .orElseThrow(CompanyNotFoundException::new);

        return repository.save(job);
    }
}
