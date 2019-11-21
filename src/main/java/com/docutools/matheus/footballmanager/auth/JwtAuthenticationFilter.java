package com.docutools.matheus.footballmanager.auth;

import com.docutools.matheus.footballmanager.FootballManagerProperties;
import com.docutools.matheus.footballmanager.dto.LoginDataDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that fetch user login and tries to generate a token to be validated
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private FootballManagerProperties properties;

	private final AuthenticationManager authenticationManager;

	public JwtAuthenticationFilter(FootballManagerProperties properties, AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		this.properties = properties;

		/* change url */
		setFilterProcessesUrl(this.properties.getAuthLoginUrl());
	}

	/**
	 * parser json from request
	 *
	 * @param request body sent by user
	 * @return dto for validate
	 */
	private LoginDataDTO parseLoginDataJson(HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(request.getInputStream(), LoginDataDTO.class);
		} catch (IOException e) {
			return new LoginDataDTO();
		}
	}

//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
//
//		return authenticationManager.authenticate(authenticationToken);
//	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		LoginDataDTO login = this.parseLoginDataJson(request);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

		return this.authenticationManager.authenticate(authentication);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
											FilterChain filterChain, Authentication authentication) {
		User user = (User) authentication.getPrincipal();

		List<String> roles = user.getAuthorities()
				.stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		byte[] signingKey = this.properties.getAuthJwtSecret().getBytes();

		String token = Jwts.builder()
				.signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", this.properties.getAuthTokenType())
				.setIssuer(this.properties.getAuthTokenIssuer())
				.setAudience(this.properties.getAuthTokenAudience())
				.setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + this.properties.getAuthExpireTime()))
				.claim("rol", roles)
				.compact();

		response.addHeader(this.properties.getAuthTokenHeader(), this.properties.getAuthTokenPrefix() + token);
	}
}
