package com.example.restspringtemplate.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.restspringtemplate.utils.Constants;

import java.security.Key;
import java.time.Clock;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.function.Function;

import static com.example.restspringtemplate.utils.Constants.TOKEN_EXPIRATION_DURATION_EXTENDED;
import static com.example.restspringtemplate.utils.Constants.TOKEN_EXPIRATION_DURATION_NORMAL;

/**
 * Class that handles JWT tokens.
 */
@Service
@RequiredArgsConstructor
public class JWTService {
	private final Clock clock;

	/**
	 * Retrieves the username from the JWT token (subject).
	 *
	 * @param token token to get the username from.
	 *
	 * @return username from the token.
	 */
	public String getUsername(String token) {
		return getClaim(token, Claims::getSubject);
	}

	/**
	 * Creates a new JWT token with the given data.
	 *
	 * @param username             username to be added to the token.
	 * @param requestURL           URL of the request.
	 * @param role                 role of the user.
	 * @param extendExpirationTime if the expiration time should be extended.
	 *
	 * @return the JWT token as a {@link String}.
	 */
	public String generateToken(String username, String requestURL,
	                            String role, boolean extendExpirationTime) {
		Instant instant = this.clock.instant();

		return Jwts.builder()
		           .setSubject(username)
		           .setIssuer(requestURL) // URL of our application
		           .claim("role", role) // Only one role is in DB
		           .setIssuedAt(Date.from(instant))
		           .setExpiration(Date.from(
				           instant.plus(
						           extendExpirationTime ?
						           TOKEN_EXPIRATION_DURATION_EXTENDED :
						           TOKEN_EXPIRATION_DURATION_NORMAL
				           )
		           ))
		           .signWith(getSecretKey(), SignatureAlgorithm.HS256)
		           .compact();
	}

	/**
	 * Creates a new JWT token for temporary access.
	 *
	 * @param username username to be added to the token.
	 * @param role     role of the user.
	 *
	 * @return the JWT token as a {@link String}.
	 */
	public static String generateTmpToken(String username, String role) {
		Instant instant = Clock.systemUTC().instant();

		return Jwts.builder()
		           .setSubject(username)
		           .claim("role", role)
		           .setIssuedAt(Date.from(instant))
		           .setExpiration(Date.from(instant.plus(1, ChronoUnit.HOURS)))
		           .signWith(getSecretKey(), SignatureAlgorithm.HS256)
		           .compact();
	}

	/**
	 * Checks if the token is invalid. If the token is invalid, it means that it
	 * is one of the following:
	 * <ul>
	 *     <li>Expired</li>
	 *     <li>Malformed</li>
	 *     <li>Signature is invalid</li>
	 *     <li>Claims are invalid</li>
	 *     <li>Token is null / blank</li>
	 *     <li>Token is not prefixed with {@link Constants#JWT_TOKEN_PREFIX}</li>
	 * </ul>
	 *
	 * @param token token to check.
	 *
	 * @return {@code true} if the token is invalid, {@code false} otherwise.
	 */
	public boolean isInvalidToken(String token) {
		try {
			getClaims(token);
			return false;
		} catch (Exception e) {
			return true;
		}
	}

	/**
	 * Gets the specified claim from the token.
	 *
	 * @param token          token to get the claim from.
	 * @param claimsResolver function that gets the claim from the token.
	 * @param <T>            type of the claim.
	 *
	 * @return the claim from the token.
	 */
	public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
		Claims claims = getClaims(token);
		return claimsResolver.apply(claims);
	}

	/**
	 * Parses the JWT token and returns the claims.
	 *
	 * @param token JWT token to be parsed and validated. It contains the prefix
	 *              {@link Constants#JWT_TOKEN_PREFIX} in front of it.
	 *
	 * @return the claims of the token.
	 */
	private Claims getClaims(@NonNull String token) {
		return Jwts.parserBuilder()
		           .setSigningKey(getSecretKey())
		           .build()
		           .parseClaimsJws(token.substring(Constants.JWT_TOKEN_PREFIX.length()))
		           .getBody();
	}

	/**
	 * @return the secret key that is used to sign the JWT token.
	 */
	private static Key getSecretKey() {
		byte[] keyBytes = Decoders.BASE64.decode(System.getenv("TOKEN_SECRET"));
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
