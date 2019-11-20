package com.docutools.matheus.footballmanager.controller;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.dto.MemberAddDTO;
import com.docutools.matheus.footballmanager.dto.MemberUpdateDTO;
import com.docutools.matheus.footballmanager.exception.MemberNotFoundException;
import com.docutools.matheus.footballmanager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("members")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/")
	public List<MemberDTO> listAll(
			@RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
			@RequestParam(required = false, defaultValue = "10") @Positive int size) {
		return this.memberService.listAllPaginated(page, size);
	}

	@GetMapping("/{id}")
	public MemberDTO fetchMember(@PathVariable("id") String uuid) {
		Optional<MemberDTO> member = this.memberService.find(UUID.fromString(uuid));

		return member.orElseThrow(MemberNotFoundException::new);
	}

	@DeleteMapping("/")
	public ResponseEntity deleteMembers(@RequestBody List<UUID> uuids) {
		this.memberService.deleteInBatch(uuids);

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteMember(@PathVariable("id") String uuid) {
		/*
		 to keep it simple, I transform it to a list to be able to reused the other function and maintain the same logic
		 could be list.of if using java9+
		 */
		this.memberService.deleteInBatch(Stream.of(UUID.fromString(uuid)).collect(Collectors.toList()));

		return ResponseEntity.ok().build();
	}

	@PostMapping("/")
	public MemberDTO addMember(@RequestBody MemberAddDTO memberAddDTO) {
		return this.memberService.addMember(memberAddDTO);
	}

	@PutMapping("/")
	public List<MemberDTO> updateMembers(@RequestBody List<MemberUpdateDTO> membersUpdateDTO) {
		return this.memberService.updateMember(membersUpdateDTO);
	}
}