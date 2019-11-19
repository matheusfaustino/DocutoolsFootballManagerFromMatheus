package com.docutools.matheus.footballmanager.repository;

import com.docutools.matheus.footballmanager.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	/**
	 * Selects role by label
	 * @param label label of the role
	 */
	@Query("select r from #{#entityName} r where r.parentId IS NULL and r.label=?1")
	public Optional<Role> findParentRoleByLabel(String label);

	/**
	 * Selects role by label
	 * @param label label of the role
	 */
	@Query("select r from #{#entityName} r where r.parentId IS NOT NULL and r.label=?1")
	public Optional<Role> findChildRoleByLabel(String label);
}
