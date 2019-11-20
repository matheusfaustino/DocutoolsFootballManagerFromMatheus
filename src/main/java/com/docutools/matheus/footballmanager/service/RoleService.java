package com.docutools.matheus.footballmanager.service;

import com.docutools.matheus.footballmanager.dto.RoleGenericDTO;
import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleService {
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * List all roles that can be selected
	 * @return list by generic role to specialized ones
	 */
	public List<RoleGenericDTO> listAllRoles() {
		Collection<Role> genericsRoles = this.roleRepository.findAllGenericRoles();

		return genericsRoles.stream()
				.map(RoleGenericDTO::convertToDto)
				.collect(Collectors.toList());
	}
}
