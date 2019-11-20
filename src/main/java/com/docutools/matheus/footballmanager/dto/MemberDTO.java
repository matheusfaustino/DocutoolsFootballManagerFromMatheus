package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.roles.TeamRoles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
	private UUID memberId;
	private String name;
	private String role;
	private Boolean firstTeam;
	private Boolean benched;

	public static MemberDTO convertToDto(Member member) {
		return new MemberDTO(member.getMemberId(), member.getName(), member.getRole().getLabel(), member.getFirstTeam(), member.getBenched());
	}
}
