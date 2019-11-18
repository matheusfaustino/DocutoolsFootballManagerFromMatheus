package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor
public class RoleDTO {
	@Positive
	@NotNull
	private Integer id;
	private String label;

	public RoleDTO(Integer id) {
		this.id = id;
	}
}
