package br.com.attomtech.gestaovagas.modules.candidate.services;

import br.com.attomtech.gestaovagas.exceptions.JobNotFoundException;
import br.com.attomtech.gestaovagas.exceptions.UserNotFoundException;
import br.com.attomtech.gestaovagas.modules.candidate.CandidateRepository;
import br.com.attomtech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import br.com.attomtech.gestaovagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.attomtech.gestaovagas.modules.company.repositories.JobRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyJobCandidateService {

    final CandidateRepository candidateRepository;
    final JobRepository jobRepository;
    final ApplyJobRepository applyJobRepository;

    public ApplyJobCandidateService(CandidateRepository candidateRepository, JobRepository jobRepository,
        ApplyJobRepository applyJobRepository) {
        this.candidateRepository = candidateRepository;
        this.jobRepository = jobRepository;
        this.applyJobRepository = applyJobRepository;
    }

    public ApplyJobEntity execute(String candidateId, String joId) {
        candidateRepository.findById(candidateId)
            .orElseThrow(UserNotFoundException::new);

        jobRepository.findById(joId)
            .orElseThrow(JobNotFoundException::new);

        var applyJob = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(joId)
            .build();

        return applyJobRepository.save(applyJob);
    }
}
