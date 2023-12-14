package br.com.attomtech.gestaovagas.modules.candidate.services;

import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import br.com.attomtech.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsByFilterService {

    final JobRepository jobRepository;

    public ListAllJobsByFilterService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public List<JobEntity> execute(String filter) {
        return jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
