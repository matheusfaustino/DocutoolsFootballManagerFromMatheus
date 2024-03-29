package com.docutools.matheus.footballmanager.controller;

import com.docutools.matheus.footballmanager.dto.RoleGenericDTO;
import com.docutools.matheus.footballmanager.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@Api(value = "Role")
public class RoleController {
	@Autowired
	private RoleService roleService;

	@ApiOperation("List all roles available in the system")
	@GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RoleGenericDTO> index() {
		return this.roleService.listAllRoles();
	}
}
