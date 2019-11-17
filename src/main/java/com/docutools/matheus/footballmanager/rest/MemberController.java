package com.docutools.matheus.footballmanager.rest;

import com.docutools.matheus.footballmanager.dto.MemberDTO;
import com.docutools.matheus.footballmanager.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("members")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@GetMapping("/")
	public List<MemberDTO> listAll() {
		return memberService.listAll();
	}
}
