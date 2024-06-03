package br.com.capelaum.job_management.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTCompanyProvider {

	@Value("${security.token.secret.company}")
	private String secretKey;

	public DecodedJWT validateToken(String bearerToken) {
		String token = bearerToken.replace("Bearer ", "");
		Algorithm algorithm = Algorithm.HMAC256(secretKey);

		try {
			var decodedJWT = JWT.require(algorithm)
					.build()
					.verify(token);

			return decodedJWT;
		} catch (JWTVerificationException e) {
//			e.printStackTrace();
			return null;
		}
	}
}
