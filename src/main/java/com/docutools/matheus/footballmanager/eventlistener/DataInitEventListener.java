package com.docutools.matheus.footballmanager.eventlistener;

import com.docutools.matheus.footballmanager.entity.Member;
import com.docutools.matheus.footballmanager.entity.Role;
import com.docutools.matheus.footballmanager.repository.MemberRepository;
import com.docutools.matheus.footballmanager.repository.RoleRepository;
import com.docutools.matheus.footballmanager.role.CoachRoles;
import com.docutools.matheus.footballmanager.role.MedicalRoles;
import com.docutools.matheus.footballmanager.role.PlayerRoles;
import com.docutools.matheus.footballmanager.role.TeamRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use to initialize database values (in this case, just the ROLES)
 */
@Component
public class DataInitEventListener {
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private MemberRepository memberRepository;

	@Transactional
	@EventListener
	public void insertRoles(ContextRefreshedEvent event) {
		if (this.roleRepository.findAll().size() > 0) {
			return;
		}

		Role coach = (new Role());
		coach.setLabel(TeamRoles.COACH.name());
		Role player = new Role();
		player.setLabel(TeamRoles.PLAYER.name());
		Role medical = new Role();
		medical.setLabel(TeamRoles.MEDICAL.name());

		/* coach specialized roles */
		Role headCoach = new Role();
		headCoach.setLabel(CoachRoles.HEAD_COACH.toString());
		headCoach.setParentId(coach);
		Role viceCoach = new Role();
		viceCoach.setLabel(CoachRoles.VICE_COACH.toString());
		viceCoach.setParentId(coach);
		Role attachTraineer = new Role();
		attachTraineer.setLabel(CoachRoles.ATTACH_TRAINEER.toString());
		attachTraineer.setParentId(coach);
		Role defenceTraineer = new Role();
		defenceTraineer.setLabel(CoachRoles.DEFENCE_TRAINEER.toString());
		defenceTraineer.setParentId(coach);
		Role goalkeeperTrainee = new Role();
		goalkeeperTrainee.setLabel(CoachRoles.GOALKEEPER_TRAINEER.toString());
		goalkeeperTrainee.setParentId(coach);

		/* player specialized roles */
		Role forwards = new Role();
		forwards.setLabel(PlayerRoles.FORWARDS.toString());
		forwards.setParentId(player);
		Role middleFielders = new Role();
		middleFielders.setLabel(PlayerRoles.MIDDLE_FIELDERS.toString());
		middleFielders.setParentId(player);
		Role defenders = new Role();
		defenders.setLabel(PlayerRoles.DEFENDERS.toString());
		defenders.setParentId(player);
		Role goalkeepers = new Role();
		goalkeepers.setLabel(PlayerRoles.GOALKEEPER.toString());
		goalkeepers.setParentId(player);

		/* medical specialized roles */
		Role doctors = new Role();
		doctors.setLabel(MedicalRoles.DOCTORS.name());
		doctors.setParentId(medical);
		Role nutritionists = new Role();
		nutritionists.setLabel(MedicalRoles.NUTRITIONISTS.name());
		nutritionists.setParentId(medical);
		Role masseurs = new Role();
		masseurs.setLabel(MedicalRoles.MASSEURS.name());
		masseurs.setParentId(medical);
		Role physiotherapists = new Role();
		physiotherapists.setLabel(MedicalRoles.PHYSIOTHERAPISTS.name());
		physiotherapists.setParentId(medical);
		Role psychologists = new Role();
		psychologists.setLabel(MedicalRoles.PSYCHOLOGISTS.name());
		psychologists.setParentId(medical);


		this.roleRepository.save(coach);
		this.roleRepository.save(player);
		this.roleRepository.save(medical);
		this.roleRepository.save(headCoach);
		this.roleRepository.save(viceCoach);
		this.roleRepository.save(attachTraineer);
		this.roleRepository.save(defenceTraineer);
		this.roleRepository.save(goalkeeperTrainee);
		this.roleRepository.save(forwards);
		this.roleRepository.save(middleFielders);
		this.roleRepository.save(defenders);
		this.roleRepository.save(goalkeepers);
		this.roleRepository.save(doctors);
		this.roleRepository.save(nutritionists);
		this.roleRepository.save(masseurs);
		this.roleRepository.save(physiotherapists);
		this.roleRepository.save(psychologists);

		Member gk = new Member();
		gk.setFirstTeam(true);
		gk.setName("Marcos");
		gk.setRole(goalkeepers);

		Member cb1 = new Member();
		cb1.setFirstTeam(true);
		cb1.setName("Roque Junior");
		cb1.setRole(defenders);

		Member cb2 = new Member();
		cb2.setFirstTeam(true);
		cb2.setName("Lucio");
		cb2.setRole(defenders);

		Member cb3 = new Member();
		cb3.setFirstTeam(true);
		cb3.setName("Edmilson");
		cb3.setRole(defenders);

		Member rwb = new Member();
		rwb.setFirstTeam(true);
		rwb.setName("Cafu");
		rwb.setRole(defenders);

		Member lwb = new Member();
		lwb.setFirstTeam(true);
		lwb.setName("Roberto Carlos");
		lwb.setRole(defenders);

		Member cm1 = new Member();
		cm1.setFirstTeam(true);
		cm1.setName("Gilberto Silva");
		cm1.setRole(middleFielders);

		Member cm2 = new Member();
		cm2.setFirstTeam(true);
		cm2.setName("Juninho");
		cm2.setRole(middleFielders);

		Member rf = new Member();
		rf.setFirstTeam(true);
		rf.setName("Ronaldinho");
		rf.setRole(forwards);

		Member cf = new Member();
		cf.setFirstTeam(true);
		cf.setName("Ronaldo");
		cf.setRole(forwards);

		Member lf = new Member();
		lf.setFirstTeam(true);
		lf.setName("Rivaldo");
		lf.setRole(forwards);

		Member cm3 = new Member();
		cm3.setFirstTeam(false);
		cm3.setBenched(true);
		cm3.setName("Kaka");
		cm3.setRole(middleFielders);

		Member hCoachBrazil = new Member();
		hCoachBrazil.setFirstTeam(true);
		hCoachBrazil.setName("Luiz Felipe Scolari");
		hCoachBrazil.setRole(headCoach);

		this.memberRepository.save(gk);
		this.memberRepository.save(cb1);
		this.memberRepository.save(cb2);
		this.memberRepository.save(cb3);
		this.memberRepository.save(rwb);
		this.memberRepository.save(lwb);
		this.memberRepository.save(cm1);
		this.memberRepository.save(cm2);
		this.memberRepository.save(rf);
		this.memberRepository.save(cf);
		this.memberRepository.save(lf);
		this.memberRepository.save(cm3);
		this.memberRepository.save(hCoachBrazil);
	}
}
