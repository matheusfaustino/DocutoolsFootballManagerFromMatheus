package com.docutools.matheus.footballmanager.rest;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.exception.MemberNotFoundException;
import com.docutools.matheus.footballmanager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("members")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/")
	public List<MemberDTO> listAll(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		return this.memberService.listAllPaginated(page, size);
	}

	@GetMapping("/{id}")
	public MemberDTO fetchMember(@PathVariable("id") String uuid) {
		Optional<MemberDTO> member = this.memberService.find(UUID.fromString(uuid));

		return member.orElseThrow(MemberNotFoundException::new);
	}
}
