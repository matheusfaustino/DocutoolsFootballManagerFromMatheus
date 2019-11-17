package com.docutools.matheus.footballmanager.entity;

import com.docutools.matheus.footballmanager.roles.TeamRoles;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * I had to put quotation marks on the name because it is a reserved word in mysql 8.0.18 (docker current mysql version)
 */
@Table(name = "\"member\"")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
	@Setter(AccessLevel.PROTECTED)
	@Column(columnDefinition = "BINARY(16)")
    private UUID memberId;

    @Column()
    private String name;

    @Enumerated(EnumType.STRING)
    private TeamRoles role;
}
