package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoleGenericDTO {
	private String label;
	private List<RoleDTO> specialization;

	public static RoleGenericDTO convertToDto(Role role) {
		return new RoleGenericDTO(
				role.getLabel(),
				role.getChildren().stream().map(RoleDTO::convertToDTO).collect(Collectors.toList())
		);
	}
}
