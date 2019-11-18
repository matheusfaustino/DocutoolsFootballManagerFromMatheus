package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
public class MemberAddDTO {
	@NotNull(message = "You should provide a name for the member")
	@Length(min = 3, max = 255)
	private String name;

	@NotNull(message = "You should pass a valid role")
	private RoleDTO role;
}