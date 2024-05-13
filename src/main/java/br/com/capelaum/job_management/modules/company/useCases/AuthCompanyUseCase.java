package br.com.capelaum.job_management.modules.company.useCases;

import br.com.capelaum.job_management.modules.company.dto.AuthCompanyDTO;
import br.com.capelaum.job_management.modules.company.repositories.CompanyRepository;
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

@Service
public class AuthCompanyUseCase {

	@Value("${security.token.secret}")
	private String secretKey;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
		var company = this.companyRepository
				.findByUsername(authCompanyDTO.getUsername())
				.orElseThrow(() -> new UsernameNotFoundException(
						"Username/Password incorrect"));

		// Verificar se senhas são iguais
		var passwordMatches = this.passwordEncoder
				.matches(authCompanyDTO.getPassword(), company.getPassword());

		if (!passwordMatches) {
			throw new AuthenticationException("Username/Password incorrect");
		}

		Algorithm algorithm = Algorithm.HMAC256(secretKey);
		var token = JWT.create().withIssuer("javagas")
				.withExpiresAt(Instant.now().plus(Duration.ofHours(3)))
				.withSubject(company.getId().toString())
				.sign(algorithm);

		return token;
	}
}
