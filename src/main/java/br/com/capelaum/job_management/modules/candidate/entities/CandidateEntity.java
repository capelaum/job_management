package br.com.capelaum.job_management.modules.candidate.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidates")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Schema(example = "Luís V. Capelletto", description = "Nome do candidato")
    private String name;

    @NotBlank(message = "O nome de usuário é obrigatório")
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaços")
    @Schema(example = "capelaum", requiredMode = RequiredMode.REQUIRED, description = "Nome de usuário do candidato")
    private String username;

    @Email(message = "O campo deve conter um e-mail válido")
    @Schema(example = "capelaum@gmail.com", requiredMode = RequiredMode.REQUIRED, description = "E-mail do candidato")
    private String email;

    @Length(min = 8, max = 100, message = "A senha deve conter entre 8 e 100 caracteres")
    @Schema(example = "password@123", minLength = 10, maxLength = 100, requiredMode = RequiredMode.REQUIRED, description = "Senha do candidato")
    private String password;

    @Schema(example = "Desenvolvedor Java", description = "Breve descrição do candidato")
    private String description;

    @Schema(example = "Java, Typescript, PHP, Python", description = "Habilidades do candidato")
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
