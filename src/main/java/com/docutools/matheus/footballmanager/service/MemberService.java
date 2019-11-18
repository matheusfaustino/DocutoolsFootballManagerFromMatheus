package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.dto.MemberAddDTO;
import com.docutools.matheus.footballmanager.dto.MemberUpdateDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.exception.MemberNotFoundException;
import com.docutools.matheus.footballmanager.exception.RoleNotFoundException;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import com.docutools.matheus.footballmanager.repository.RoleRepository;
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

	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Use extracted method to throws exception on .steam()
	 * @param uuid
	 * @return
	 */
	private Member findMemberByUuid(UUID uuid) {
		return this.membersRepository.findById(uuid).orElseThrow(MemberNotFoundException::new);
	}

	/**
	 * Use extracted method to throws exception
	 * @param id
	 * @return
	 */
	private Role findRoleById(Integer id) {
		return this.roleRepository.findById(id).orElseThrow(RoleNotFoundException::new);
	}

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
		/* @todo go back here to use extracted method to throws exception on map stream */
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

	/**
	 * Validate data and persist in the database
	 * @param memberAddDTO data sent by the client (usually)
	 * @return a version of member to return to the client
	 */
	public MemberDTO addMember(MemberAddDTO memberAddDTO) {
		Role role = this.roleRepository.findById(memberAddDTO.getRole().getId()).orElseThrow(RoleNotFoundException::new);
		Member member = new Member();
		member.setName(memberAddDTO.getName());
		member.setRole(role);

		return MemberDTO.convertToDto(this.membersRepository.saveAndFlush(member));
	}

	@Transactional
	public List<MemberDTO> updateMember(List<MemberUpdateDTO> membersUpdateDTO) {
		return membersUpdateDTO.stream()
				/* convert all DTO to entity to update */
				.map(updateDTO -> {
					Member member = this.findMemberByUuid(updateDTO.getMemberId());
					Role role = this.findRoleById(updateDTO.getRole().getId());

					member.setRole(role);
					member.setName(updateDTO.getName());

					/* I am using save here to make sure that if an exception is thrown i can rollback the change of others entities */
					this.membersRepository.save(member);

					return member;
				})
				.map(MemberDTO::convertToDto)
				.collect(Collectors.toList());
	}
}
