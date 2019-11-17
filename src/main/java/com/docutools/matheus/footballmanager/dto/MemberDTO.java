package com.docutools.matheus.footballmanager.dto;

import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.roles.TeamRoles;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberDTO {
	private UUID memberId;
	private String name;
	private TeamRoles role;

	public static MemberDTO convertToDto(Member member) {
		return new MemberDTO(member.getMemberId(), member.getName(), member.getRole());
	}
}
