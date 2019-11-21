package com.docutools.matheus.footballmanager.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class LoginDataDTO {
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;
}
