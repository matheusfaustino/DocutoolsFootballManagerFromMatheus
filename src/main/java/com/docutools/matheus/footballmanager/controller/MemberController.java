package com.docutools.matheus.footballmanager.controller;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.dto.MemberAddDTO;
import com.docutools.matheus.footballmanager.dto.MemberUpdateDTO;
import com.docutools.matheus.footballmanager.exception.MemberNotFoundException;
import com.docutools.matheus.footballmanager.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Api(value = "Member")
@RestController
@RequestMapping("/api/members")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@ApiOperation("List all members from the team")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MemberDTO> listAll(
			@RequestParam(required = false, defaultValue = "0") @PositiveOrZero int page,
			@RequestParam(required = false, defaultValue = "10") @Positive int size) {
		return this.memberService.listAllPaginated(page, size);
	}

	@ApiOperation("Get specific members. Id comma-separated is accept")
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MemberDTO> fetchMember(@ApiParam(name = "id", value = "Comma-separated uuid", required = true) @PathVariable("id") List<String> uuids) {
		return uuids.stream().map(uuid -> this.memberService.find(UUID.fromString(uuid))).collect(Collectors.toList());
	}

	@ApiOperation("Bulk delete multiple team members")
	@DeleteMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteMembers(@RequestBody List<UUID> uuids) {
		this.memberService.deleteInBatch(uuids);

		return ResponseEntity.ok().build();
	}

	@ApiOperation("Delete only one member")
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity deleteMember(@PathVariable("id") String uuid) {
		/*
		 to keep it simple, I transform it to a list to be able to reused the other function and maintain the same logic
		 could be list.of if using java9+
		 */
		this.memberService.deleteInBatch(Stream.of(UUID.fromString(uuid)).collect(Collectors.toList()));

		return ResponseEntity.ok().build();
	}

	@ApiOperation("Add new member to the team")
	@PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public MemberDTO addMember(@RequestBody MemberAddDTO memberAddDTO) {
		return this.memberService.addMember(memberAddDTO);
	}

	@ApiOperation("Bulk update of team's members")
	@PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<MemberDTO> updateMembers(@RequestBody List<MemberUpdateDTO> membersUpdateDTO) {
		return this.memberService.updateMember(membersUpdateDTO);
	}
}
