package br.com.capelaum.job_management.security;

import br.com.capelaum.job_management.providers.JWTCompanyProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityCompanyFilter extends OncePerRequestFilter {

	@Autowired
	private JWTCompanyProvider jwtCompanyProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//		SecurityContextHolder.getContext().setAuthentication(null);
		String bearerToken = request.getHeader("Authorization");

		if (request.getRequestURI().startsWith("/company") && bearerToken != null) {
			var token = this.jwtCompanyProvider.validateToken(bearerToken);

			if (token == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}

			request.setAttribute("company_id", token.getSubject());

			var roles = token.getClaim("roles").asList(Object.class);
			var grants = roles.stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase()))
					.toList();

			UsernamePasswordAuthenticationToken auth =
					new UsernamePasswordAuthenticationToken(token.getSubject(), null, grants);

			SecurityContextHolder.getContext().setAuthentication(auth);
		}

		filterChain.doFilter(request, response);
	}
}
