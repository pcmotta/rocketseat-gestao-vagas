package br.com.attomtech.gestaovagas.modules.candidate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Data
@Entity(name = "candidate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {
    @Index(name = "candidate_username_index", columnList = "username", unique = true)
})
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String name;

    @Pattern(regexp = "\\S+", message = "O campo (username) não deve conter espaços")
    String username;

    @Email(message = "O campo (email) deve conter um e-mail válido")
    String email;

    @Length(min = 10, max = 100, message = "A senha deve ter entre 10 e 100 caracteres")
    String password;
    String description;
    String curriculum;

    @CreationTimestamp
    LocalDateTime createdAt;
}
