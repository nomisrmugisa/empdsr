package org.pdsr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.pdsr.enums.GroupRoles;
import org.pdsr.master.model.country_table;
import org.pdsr.master.model.group_table;
import org.pdsr.master.model.user_table;
import org.pdsr.master.repo.CountryTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PreDataConfig {

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private CountryTableRepository countryRepo;

	@PostConstruct
	public void preloadingDatabase() {

		Optional<user_table> webadminuser = userRepo.findById(CONSTANTS.WEBADMIN_ID);

		if (!webadminuser.isPresent()) {

			List<group_table> groupList = new ArrayList<group_table>();
			groupList.add(new group_table(GroupRoles.ROLE_ENTRY.getId(), GroupRoles.ROLE_ENTRY.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_AUDIT.getId(), GroupRoles.ROLE_AUDIT.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_TASKS.getId(), GroupRoles.ROLE_TASKS.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_SETUP.getId(), GroupRoles.ROLE_SETUP.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_VIEWS.getId(), GroupRoles.ROLE_VIEWS.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_NATIONAL.getId(), GroupRoles.ROLE_NATIONAL.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_REGIONAL.getId(), GroupRoles.ROLE_REGIONAL.getDescription()));
			groupList.add(new group_table(GroupRoles.ROLE_DISTRICT.getId(), GroupRoles.ROLE_DISTRICT.getDescription()));

			user_table webadmin = new user_table();
			webadmin.setUsername("webadmin");
			webadmin.setUseremail("webadmin@olincgroup.com");
			webadmin.setUserfullname("Root Administrator");
			webadmin.setUsercontact("+000000000000");
			webadmin.setAlerted(true);
			webadmin.setEnabled(true);
			webadmin.setPassword(bCrypt.encode("admin"));
			webadmin.setGroups(groupList);
			userRepo.save(webadmin);
			
			country_table country = new country_table();
			country.setCountry_uuid(CONSTANTS.LICENSE_ID);
			country.setCountry_name(CONSTANTS.LICENSE_COUNTRY);
			countryRepo.save(country);
			
		}

	}

}