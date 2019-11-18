package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.exception.MemberNotFoundException;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * Retrieves information from a single player
	 * @param uuid member's identification
	 * @return The member object or null
	 */
	public Optional<MemberDTO> find(UUID uuid) {
		Optional<Member> row = this.membersRepository.findById(uuid);

		return row.map(MemberDTO::convertToDto);
	}

	/**
	 * Deletes logically all members
	 * @param uuids representation for delete members
	 */
	@Transactional
	public void deleteInBatch(List<UUID> uuids) {
		/**
		 * I do not used .stream() here because the IDE recommended using forEach without .stream
		 */
		uuids.forEach(uuid -> {
					/*
					  here i am trying to validate if the item exists before deleting.
					  I tried using two maps, forEach and etc, but none happened the way I would like to
					 */
					this.membersRepository.findById(uuid).orElseThrow(MemberNotFoundException::new);
					this.membersRepository.delete(uuid);
				});
	}
}
