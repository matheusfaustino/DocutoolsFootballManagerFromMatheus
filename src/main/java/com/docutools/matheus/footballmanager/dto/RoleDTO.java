package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleDTO {
	@Positive
	@NotNull
	private Integer id;
	private String label;

	public RoleDTO(Integer id) {
		this.id = id;
	}

	public static RoleDTO convertToDTO(Role role) {
		return new RoleDTO(role.getId(), role.getLabel());
	}
}
