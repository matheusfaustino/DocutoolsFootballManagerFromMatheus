package com.docutools.matheus.footballmanager.repository;

import com.docutools.matheus.footballmanager.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
	/**
	 * Create method that under the hood delete item logically
	 * @param id uuid of the item
	 */
	@Query("update #{#entityName} e set e.deleted=true where e.id=?1")
	@Modifying
	public void delete(UUID id);
}
