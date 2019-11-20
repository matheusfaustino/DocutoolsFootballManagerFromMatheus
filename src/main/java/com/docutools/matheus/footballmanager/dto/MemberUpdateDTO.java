package com.docutools.matheus.footballmanager.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateDTO {
	@NotNull(message = "You should pass the member's identification")
	private UUID memberId;

	@NotNull(message = "You should provide a name for the member")
	@Length(min = 3, max = 255)
	private String name;

	@NotNull(message = "You should pass a valid role")
	private RoleDTO role;

	private Optional<Boolean> firstTeam;
	private Optional<Boolean> benched;
}
