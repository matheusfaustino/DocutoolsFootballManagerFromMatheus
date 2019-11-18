package com.docutools.matheus.footballmanager.entity;

import com.docutools.matheus.footballmanager.roles.TeamRoles;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * I had to put quotation marks on the name because it is a reserved word in mysql 8.0.18 (docker current mysql version)
 */
@Table(name = "\"member\"")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Member {
    @Id
    @GeneratedValue
	@Setter(AccessLevel.PROTECTED)
	@Column(columnDefinition = "BINARY(16)")
    private UUID memberId;

    @Column()
	@NotNull(message = "You should provide a name for the member")
	@Length(min = 3, max = 255)
    private String name;

    @ManyToOne
	@NotNull(message = "You should pass a role for the member")
    private Role role;

	/**
	 * I set it because in the database the default is right, but the insert made by hibernate will force the NULL value
	 */
	@Column(columnDefinition = "boolean default false")
    private Boolean deleted = false;
}
