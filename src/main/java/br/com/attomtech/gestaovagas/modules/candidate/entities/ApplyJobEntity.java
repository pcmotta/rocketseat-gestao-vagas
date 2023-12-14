package br.com.attomtech.gestaovagas.modules.candidate.entities;

import br.com.attomtech.gestaovagas.modules.candidate.CandidateEntity;
import br.com.attomtech.gestaovagas.modules.company.entities.JobEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "applyJobs")
@Table(name = "applyJobs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyJobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne
    @JoinColumn(name = "candidate_id", insertable = false, updatable = false)
    CandidateEntity candidateEntity;

    @ManyToOne
    @JoinColumn(name = "job_id", insertable = false, updatable = false)
    JobEntity jobEntity;

    @Column(name = "candidate_id")
    String candidateId;

    @Column(name = "job_id")
    String jobId;

    @CreationTimestamp
    LocalDateTime createdAt;
}

