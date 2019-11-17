package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {
	@Autowired
	private MemberRepository membersRepository;

	public List<MemberDTO> listAll() {
		List<Member> members = this.membersRepository.findAll();

		return members.stream()
				.map(MemberDTO::convertToDto)
				.collect(Collectors.toList());
	}
}
