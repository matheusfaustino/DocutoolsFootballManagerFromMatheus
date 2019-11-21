package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.role.TeamRoles;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;
import java.util.UUID;

@ApiModel
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
	private UUID memberId;
	private String name;
	private String role;

	@ApiModelProperty(dataType = "java.lang.Boolean")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Optional<Boolean> firstTeam;
	@ApiModelProperty(dataType = "java.lang.Boolean")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Optional<Boolean> benched;

	public static MemberDTO convertToDto(Member member) {
		Role parent = member.getRole().getParentId() != null ? member.getRole().getParentId() : member.getRole();
		boolean isPlayer = parent.getLabel().equalsIgnoreCase(TeamRoles.PLAYER.name());
		if (isPlayer) {
			return new MemberDTO(member.getMemberId(), member.getName(), member.getRole().getLabel(), Optional.of(member.getFirstTeam()), Optional.of(member.getBenched()));
		} else {
			return new MemberDTO(member.getMemberId(), member.getName(), member.getRole().getLabel(), null, null);
		}
	}
}
