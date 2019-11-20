package com.docutools.matheus.footballmanager;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

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
}
