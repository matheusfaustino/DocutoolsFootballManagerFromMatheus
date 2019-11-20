package com.docutools.matheus.footballmanager.validator;

import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.exception.MaximumDoctorReachedException;
import com.docutools.matheus.footballmanager.exception.MaximumHeadCoachReachedException;
import com.docutools.matheus.footballmanager.exception.MaximumPlayersReachedException;
import com.docutools.matheus.footballmanager.roles.CoachRoles;
import com.docutools.matheus.footballmanager.roles.MedicalRoles;
import com.docutools.matheus.footballmanager.roles.TeamRoles;
import com.docutools.matheus.footballmanager.utils.RoleUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * This is a business logic validation related to the whole time
 * Very opinionated way of dealing with the rules, I tried to make it more concise, readable and clear the team's constraints
 */
@Service
public class TeamConstraints {
	@Value("${application.constraints.max_player}")
	private int maxPlayerQuantity;

	@Value("${application.constraints.max_head_coach}")
	private int maxHeadCoach;

	@Value("${application.constraints.max_doctor}")
	private int maxDoctor;

	@Autowired
	private RoleUtils roleUtils;

	/**
	 * Encapsulate the validation of a new member into the team team
	 * @param member Member to be added
	 */
	public void validateMemberAddition(Member member) {
		/* check if the role is a variation of player (a really opinionated way of selection parent roles) */
		Role parentRole = member.getRole().getParentId() != null ? member.getRole().getParentId() : member.getRole();
		Role currentRole = member.getRole();

		boolean isPlayer = parentRole.getLabel().equalsIgnoreCase(TeamRoles.PLAYER.name());
		if (isPlayer && !this.canAddPlayer()) {
			throw new MaximumPlayersReachedException();
		}

		/* it's string because this enum has space between words */
		boolean isHeadCoach = currentRole.getLabel().equalsIgnoreCase(CoachRoles.HEAD_COACH.toString());
		if (isHeadCoach && !this.canAddHeadCoach()) {
			throw new MaximumHeadCoachReachedException();
		}

		boolean isDoctor = currentRole.getLabel().equalsIgnoreCase(MedicalRoles.DOCTORS.name());
		if (isDoctor && !this.canAddDoctor()) {
			throw new MaximumDoctorReachedException();
		}
	}

	/**
	 * Function responsible just for checking if can add a player or not
	 * I separated the functions because it would be too complex to maintain a functions that deals with insert and update
	 * , because update checking is way complex than insert. So let's keep it simple at first.
	 * @return If can or can't add one more player
	 */
	private Boolean canAddPlayer() {
		Role playerRole = this.roleUtils.findParentRole(TeamRoles.PLAYER);
		long countPlayerMembers = this.roleUtils.countAllMembersFromRole(playerRole);

		return countPlayerMembers < this.maxPlayerQuantity;
	}

	/**
	 * Helper function that tells if it is allowed to add a new head coach (following the same pattern of the first one)
	 * @return if can or can't add a head coach
	 */
	private Boolean canAddHeadCoach() {
		Role headCoachRole = this.roleUtils.findChildRole(CoachRoles.HEAD_COACH.toString());
		int countHeadCoach = headCoachRole.getMembers().size();

		return countHeadCoach < this.maxHeadCoach;
	}

	/**
	 * Helper function to check if can add a doctor to the team (following the same pattern of the first one)
	 * @return if can or can't add a doctor
	 */
	private Boolean canAddDoctor() {
		Role doctorRole = this.roleUtils.findChildRole(MedicalRoles.DOCTORS.name());
		int countDoctor = doctorRole.getMembers().size();

		return countDoctor < this.maxDoctor;
	}












	/**
	 * Helper function that encapsulate the validation logic for adding more players, check if can add one more player
	 * Warning: it is not well prepared for parallel processing
	 * @return if can or can't add more players
	 */
//	public Boolean canUpdateMorePlayer(List<Member> members) {
//		Role playerRole = this.roleUtils.findParentRole(TeamRoles.PLAYER);
//		playerRole.getChildren()
//				.stream()
//				.map(Role::getMembers)
//				.filter(member -> !members.contains(member) && )
//		int countPlayerMembers = this.roleUtils.countAllMembersFromRole(playerRole);

//		return (countPlayerMembers + quantityPlayersToAdd) <= this.maxPlayerQuantity;
//	}


}
