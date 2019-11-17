package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MemberService {
	@Autowired
	private MemberRepository membersRepository;

	/**
	 * List all members from the team
	 * @param page number of the page wanted
	 * @param size the maximum size of the data
	 * @return List of all members based on page and size
	 */
	public List<MemberDTO> listAllPaginated(int page, int size) {
		Page<Member> members = this.membersRepository.findAll(PageRequest.of(page, size));

		return members.stream()
				.map(MemberDTO::convertToDto)
				.collect(Collectors.toList());
	}
}
