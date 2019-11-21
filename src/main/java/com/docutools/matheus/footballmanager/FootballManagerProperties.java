package com.docutools.matheus.footballmanager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@ConfigurationProperties("football.manager")
@Validated
public class FootballManagerProperties {
	/**
	 * Maximum player allowed in the team
	 */
	@Positive
	private Integer maxPlayer;

	/**
	 * Maximum players in first team
	 */
	@PositiveOrZero
	private Integer maxPlayerFirstTeam;

	/**
	 * Maximum bench players
	 */
	@PositiveOrZero
	private Integer maxPlayerSubstitutes;

	/**
	 * Maximum head coach member
	 */
	@PositiveOrZero
	private Integer maxHeadCoach;

	/**
	 * Maximum doctor member
	 */
	@PositiveOrZero
	private Integer maxDoctor;

	@NotNull
	private String projectName;

	@NotNull
	private String projectDescription;

	@NotNull
	private String projectVersion;

	/**
	 * Url to login the token
	 */
	@NotNull
	private String authLoginUrl = "/api/login";

	/**
	 * Secret key to generate the tokens as salt
	 */
	@NotNull
	@NotEmpty
	private String authJwtSecret;

	/**
	 * Header's name
	 */
	private String authTokenHeader = "Authorization";

	/**
	 * Prefix header's value
	 */
	private String authTokenPrefix = "Bearer ";

	/**
	 * Token's type
	 */
	private String authTokenType = "JWT";

	private String authTokenIssuer = "secure-api";

	private String authTokenAudience = "secure-app";

	/**
	 * Expiration time for the token
	 */
	@NotNull
	@Positive
	private int authExpireTime = 3600000;

	/**
	 * Username to get the token
	 */
	@NotNull
	@NotEmpty
	private String authUsername;

	/**
	 * Password to get the token
	 */
	@NotNull
	@NotEmpty
	private String authPassword;
}
