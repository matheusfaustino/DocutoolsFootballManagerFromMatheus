package com.docutools.matheus.footballmanager.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter(AccessLevel.PROTECTED)
	private int id;

	@Column(length = 30)
	private String label;

	@ManyToOne
	private Role parentId;

	@OneToMany(mappedBy = "parentId")
	private Collection<Role> children;

	@OneToMany(mappedBy = "role")
	private Collection<Member> members;
}
