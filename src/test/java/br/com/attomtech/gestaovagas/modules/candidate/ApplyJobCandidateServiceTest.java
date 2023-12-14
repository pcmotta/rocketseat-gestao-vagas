package br.com.attomtech.gestaovagas.modules.candidate;

import br.com.attomtech.gestaovagas.exceptions.JobNotFoundException;
import br.com.attomtech.gestaovagas.exceptions.UserNotFoundException;
import br.com.attomtech.gestaovagas.modules.candidate.entities.ApplyJobEntity;
import br.com.attomtech.gestaovagas.modules.candidate.repositories.ApplyJobRepository;
import br.com.attomtech.gestaovagas.modules.candidate.services.ApplyJobCandidateService;
import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import br.com.attomtech.gestaovagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateServiceTest {

    @InjectMocks
    ApplyJobCandidateService applyJobCandidateService;

    @Mock
    CandidateRepository candidateRepository;

    @Mock
    JobRepository jobRepository;

    @Mock
    ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not foumd")
    public void shouldNotBeAbleToApplyJobWithCandidateNotFound() {
        try {
            applyJobCandidateService.execute(null, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Shoul no be able to apply job with job not found")
    public void shouldNotBeAbleToApplyJobWithJobNotFount() {
        var candidateId = UUID.randomUUID().toString();
        var candidate = CandidateEntity.builder()
            .id(candidateId)
            .build();

        when(candidateRepository.findById(candidateId))
            .thenReturn(Optional.ofNullable(candidate));

        try {
            applyJobCandidateService.execute(candidateId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(JobNotFoundException.class);
        }
    }

    @Test
    @DisplayName("Shoulb be able to create a new apply job")
    public void shouldBeAbleToCreateANewApplyJob() {
        var candidateId = UUID.randomUUID().toString();
        var jobId = UUID.randomUUID().toString();

        var applyJob = ApplyJobEntity.builder()
            .candidateId(candidateId)
            .jobId(jobId)
            .build();

        var applyJobCreated = ApplyJobEntity.builder()
            .id(UUID.randomUUID().toString())
            .build();

        when(candidateRepository.findById(candidateId)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(jobId)).thenReturn(Optional.of(new JobEntity()));
        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateService.execute(candidateId, jobId);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
