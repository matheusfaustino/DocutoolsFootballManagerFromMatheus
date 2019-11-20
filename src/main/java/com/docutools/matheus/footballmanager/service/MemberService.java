package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.dto.MemberAddDTO;
import com.docutools.matheus.footballmanager.dto.MemberUpdateDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.exception.*;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import com.docutools.matheus.footballmanager.repository.RoleRepository;
import com.docutools.matheus.footballmanager.validator.TeamConstraints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;

import javax.xml.validation.Validator;
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

	@Autowired
	private TeamConstraints teamConstraints;

	@Autowired
	private SmartValidator validator;

	/**
	 * Use extracted method to throws exception on .steam()
	 * @param uuid member's identification
	 * @return member's object
	 */
	private Member findMemberByUuid(UUID uuid) {
		return this.membersRepository.findById(uuid).orElseThrow(MemberNotFoundException::new);
	}

	/**
	 * Use extracted method to throws exception
	 * @param id role's identification
	 * @return role's object
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
		/* @todo add orderby and role */
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
		/* @todo make use of the exception */
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
					Member member = this.findMemberByUuid(uuid);
					this.membersRepository.delete(member);
				});
	}

	/**
	 * Validate data and persist in the database
	 * @param memberAddDTO data sent by the client (usually)
	 * @return a version of member to return to the client
	 */
	public MemberDTO addMember(MemberAddDTO memberAddDTO) {
		Role role = this.roleRepository.findById(memberAddDTO.getRole().getId()).orElseThrow(RoleNotFoundException::new);

		/*
		 I tried to convert back the DTO to entity, but I could not achieve without breaking SOLID principles
		 */
		Member member = new Member();
		member.setName(memberAddDTO.getName());
		member.setRole(role);
		member.setBenched(memberAddDTO.getBenched().orElse(false));
		member.setFirstTeam(memberAddDTO.getFirstTeam().orElse(false));

		this.teamConstraints.validateMemberAddition(member);

		return MemberDTO.convertToDto(this.membersRepository.saveAndFlush(member));
	}

	/**
	 * Update members data
	 * @param membersUpdateDTO list of members to be updated
	 * @return the view object to the client
	 */
	@Transactional
	public List<MemberDTO> updateMember(List<MemberUpdateDTO> membersUpdateDTO) {
		/*
		 If there is tuples in Java 8 will use it to keep track of both object, but without I will already alter the object to run the validation from the entity
		 This is implementation is not pure functional, because there are side effects such as Exceptions and object modification (improve it to be pure functional)
		 */
		List<Member> rowsMembers = membersUpdateDTO.stream()
				.map(dto -> {
					Member member = this.findMemberByUuid(dto.getMemberId());
					Role role = this.findRoleById(dto.getRole().getId());

					member.setRole(role);
					member.setName(dto.getName());
					member.setBenched(dto.getBenched().orElse(false));
					member.setFirstTeam(dto.getFirstTeam().orElse(false));

					/* I am using save here to make sure that if an exception is thrown i can rollback the change of others entities */
					this.membersRepository.save(member);

					return member;
				})
				.collect(Collectors.toList());

		/* once the data validated, I update the data and persist */
		this.teamConstraints.validateMembersAddition(rowsMembers);

		return rowsMembers.stream()
				.map(MemberDTO::convertToDto)
				.collect(Collectors.toList());
	}
}
