package br.com.capelaum.job_management.modules.candidate.useCases;

import br.com.capelaum.job_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.capelaum.job_management.modules.candidate.dto.AuthCandidateResponseDTO;
import br.com.capelaum.job_management.modules.candidate.repositories.CandidateRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

	@Value("${security.token.secret.candidate}")
	private String secretKey;

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
		var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
				.orElseThrow(() -> {
					throw new UsernameNotFoundException("Username/Password incorrect");
				});

		var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(),
				candidate.getPassword());

		if (!passwordMatches) {
			throw new AuthenticationException("Username/Password incorrect");
		}

		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		var expiresAt = Instant.now().plus(Duration.ofHours(3));
		var token = JWT.create()
				.withIssuer("javagas")
				.withSubject(candidate.getId().toString())
				.withClaim("roles", Arrays.asList("candidate"))
				.withExpiresAt(expiresAt)
				.sign(algorithm);

		AuthCandidateResponseDTO authCandidateResponse = AuthCandidateResponseDTO.builder()
				.access_token(token)
				.expires_at(expiresAt.toEpochMilli())
				.build();

		return authCandidateResponse;
	}
}
