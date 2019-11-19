package com.docutools.matheus.footballmanager.utils;

import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.exception.RoleNotFoundException;
import com.docutools.matheus.footballmanager.repository.RoleRepository;
import com.docutools.matheus.footballmanager.roles.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class helps in turning the code more readable in role entity manipulation
 *
 * I would name it just Role, but java does not have the alias feature for importing, so it would be not readable to have full qualified name I think
 */
@Service
public class RoleUtils {
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Count all members from a role (Created because of the constraint about 23 players)
	 * @param role the role to search the members in (it makes sense if it is a general role)
	 * @return How many members are in the role or int the subclasses of that role
	 */
	public long countAllMembersFromRole(Role role) {
		return role.getChildren().stream()
				/* i am going just one level down because i thought that knowing how the data is persisted I do not need create a generic function */
				.mapToLong(child -> child.getMembers().size())
				.sum();
	}

	/**
	 * Just get parent roles
	 * @param role possible parent roles
	 * @return role's object
	 */
	public Role findParentRole(TeamRoles role) {
		return this.roleRepository.findParentRoleByLabel(role.name()).orElseThrow(RoleNotFoundException::new);
	}

	/**
	 * Just get parent roles
	 * @param role child role as string, because we have more than one enum type for it
	 * @return role's object
	 */
	public Role findChildRole(String role) {
		return this.roleRepository.findChildRoleByLabel(role).orElseThrow(RoleNotFoundException::new);
	}
}