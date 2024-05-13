package br.com.capelaum.job_management.modules.company.useCases;

import br.com.capelaum.job_management.exceptions.UserFoundException;
import br.com.capelaum.job_management.modules.company.entities.CompanyEntity;
import br.com.capelaum.job_management.modules.company.repositories.CompanyRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public CompanyEntity execute(@NotNull CompanyEntity companyEntity) {

		this.companyRepository
				.findByUsernameOrEmail(
						companyEntity.getUsername(),
						companyEntity.getEmail())
				.ifPresent((user) -> {
					throw new UserFoundException();
				});

		var encodedPassword = passwordEncoder
				.encode(companyEntity.getPassword());
		companyEntity.setPassword(encodedPassword);

		return this.companyRepository.save(companyEntity);
	}
}
