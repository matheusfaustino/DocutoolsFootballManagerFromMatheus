package com.docutools.matheus.footballmanager.controller;

import com.docutools.matheus.footballmanager.dto.RoleGenericDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("roles")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@GetMapping("/")
	public List<RoleGenericDTO> index() {
		return this.roleService.listAllRoles();
	}
}
