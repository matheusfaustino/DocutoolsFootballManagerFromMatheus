package com.docutools.matheus.footballmanager.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Optional;
import java.util.UUID;

@ApiModel
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateDTO {
	@NotNull(message = "You should pass the member's identification")
	private UUID memberId;

	@NotNull(message = "You should provide a name for the member")
	@NotBlank(message = "You should provide a name for the member")
	@Length(min = 3, max = 255)
	private String name;

	@ApiModelProperty(dataType = "java.lang.Integer")
	@NotNull(message = "You should pass a valid role")
	private RoleDTO role;

	@ApiModelProperty(dataType = "java.lang.Boolean")
	private Optional<Boolean> firstTeam;

	@ApiModelProperty(dataType = "java.lang.Boolean")
	private Optional<Boolean> benched;
}
