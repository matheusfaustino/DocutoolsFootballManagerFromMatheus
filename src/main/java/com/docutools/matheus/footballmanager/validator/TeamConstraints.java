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
	 * Validate new members as a list, I created it separated from the new member because the manipulation with more that two members is more complex.
	 * So I tried to keep it simple and easy to maintain
	 */
	public void validateMembersAddition(List<Member> members) {
		if (!this.canAddMorePlayers(members)) {
			throw new MaximumPlayersReachedException();
		}

		if (!this.canAddMoreHeadCoach(members)) {
			throw new MaximumHeadCoachReachedException();
		}

		if (!this.canAddMoreDoctor(members)) {
			throw new MaximumDoctorReachedException();
		}
	}

	/**
	 * I think this is the most difficult validation in the project. Because It deals with members turning into players
	 * and players that aren't players anymore.
	 * I tried to separated well each iteration to be readable and be logical. I will be frank:
	 * 		I think i could come up with something better with more time working with stream and thinking more functional.
	 * @param members members sent by the client
	 * @return if can or cannot add more players, considering all the members sent
	 */
	private Boolean canAddMorePlayers(List<Member> members) {
		Role playerRole = this.roleUtils.findParentRole(TeamRoles.PLAYER);

		/* get all uuids to be able to filter which players were sent in the streams */
		List<UUID> membersSentUuid = members.stream()
				.map(Member::getMemberId)
				.collect(Collectors.toList());

		/* get all members that are players right now */
		List<Member> currentPlayerMembers = playerRole.getChildren().stream()
				.flatMap(players -> players.getMembers().stream())
				.collect(Collectors.toList());

		/* get all members sent as players */
		List<Member> membersPlayerSent = members.stream()
				.filter(member -> member.getRole().getParentId().equals(playerRole))
				.collect(Collectors.toList());

		/* members that were players or are players yet. Use it to calculate the slots available */
		List<Member> playersThatChangedRoleOrArePlayersYet = currentPlayerMembers.stream()
				.filter(player -> membersSentUuid.contains(player.getMemberId()))
				.collect(Collectors.toList());

		/*
		 Calculate the slots available based on the players that are not players more and the players that still players.
		 For the last one, they will be recounted in the members sent as players
		 */
		int occupiedSlots = currentPlayerMembers.size() - playersThatChangedRoleOrArePlayersYet.size();

		return (occupiedSlots + membersPlayerSent.size()) <= this.maxPlayerQuantity;
	}

	private Boolean canAddMoreHeadCoach(List<Member> members) {
		Role headCoachRole = this.roleUtils.findChildRole(CoachRoles.HEAD_COACH.toString());

		/* list the ids from members  */
		List<UUID> uuidMembersSentHeadCoach = members.stream()
				.filter(member -> member.getRole().equals(headCoachRole))
				.map(Member::getMemberId)
				.collect(Collectors.toList());

		List<Member> coachesSentAsCoach = headCoachRole.getMembers().stream()
				.filter(member -> uuidMembersSentHeadCoach.contains(member.getMemberId()))
				.collect(Collectors.toList());

		int occupiedSlots = headCoachRole.getMembers().size() - coachesSentAsCoach.size();

		return (occupiedSlots + uuidMembersSentHeadCoach.size()) <= this.maxHeadCoach;
	}

	private Boolean canAddMoreDoctor(List<Member> members) {
		Role doctorRole = this.roleUtils.findChildRole(MedicalRoles.DOCTORS.name());

		/* list the ids from members  */
		List<UUID> uuidMembersSentDoctor = members.stream()
				.filter(member -> member.getRole().equals(doctorRole))
				.map(Member::getMemberId)
				.collect(Collectors.toList());

		List<Member> doctorSentAsCoach = doctorRole.getMembers().stream()
				.filter(member -> uuidMembersSentDoctor.contains(member.getMemberId()))
				.collect(Collectors.toList());

		int occupiedSlots = doctorRole.getMembers().size() - doctorSentAsCoach.size();

		return (occupiedSlots + uuidMembersSentDoctor.size()) <= this.maxDoctor;
	}
}