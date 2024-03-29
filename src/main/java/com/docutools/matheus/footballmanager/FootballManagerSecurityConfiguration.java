package com.docutools.matheus.footballmanager;

import com.docutools.matheus.footballmanager.auth.JwtAuthenticationFilter;
import com.docutools.matheus.footballmanager.auth.JwtAuthorizationFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class FootballManagerSecurityConfiguration extends WebSecurityConfigurerAdapter {
	private static final Logger log = LogManager.getLogger(JwtAuthorizationFilter.class);

	@Autowired
	private FootballManagerProperties properties;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and()
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/api/auth/**").authenticated()
				.antMatchers("/api/login").authenticated()
				.antMatchers("/api/login*").authenticated()
				.anyRequest().permitAll()
				.and()
				.addFilter(new JwtAuthenticationFilter(this.properties, authenticationManager()))
				.addFilter(new JwtAuthorizationFilter(this.properties, authenticationManager()))
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("api")
				.password(passwordEncoder().encode("apidocu"))
				.authorities("ROLE_USER");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

		return source;
	}
}
