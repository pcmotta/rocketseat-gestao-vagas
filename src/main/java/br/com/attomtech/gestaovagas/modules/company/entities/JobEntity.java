package br.com.attomtech.gestaovagas.modules.company.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity(name = "job")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String description;
    String benefits;
    String level;

    @ManyToOne
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    CompanyEntity company;

    @Column(name = "company_id")
    String companyId;

    @CreationTimestamp
    LocalDateTime createdAt;
}
