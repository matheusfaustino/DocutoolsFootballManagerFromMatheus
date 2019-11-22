package com.docutools.matheus.footballmanager.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginDataDTO {
	@NotEmpty
	@NotBlank
	private String username;

	@NotEmpty
	@NotBlank
	private String password;
}
