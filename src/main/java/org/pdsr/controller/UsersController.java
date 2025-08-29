package org.pdsr.controller;

import java.security.Principal;

import org.pdsr.CONSTANTS;
import org.pdsr.master.model.sync_table;
import org.pdsr.master.model.user_table;
import org.pdsr.master.repo.FacilityTableRepository;
import org.pdsr.master.repo.GroupTableRepository;
import org.pdsr.master.repo.SyncTableRepository;
import org.pdsr.master.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/controls/users")
public class UsersController {

	@Autowired
	private SyncTableRepository syncRepo;

	@Autowired
	private UserTableRepository userRepo;

	@Autowired
	private GroupTableRepository groupRepo;

	@Autowired
	private FacilityTableRepository facRepo;

	@Autowired
	private BCryptPasswordEncoder bCrypt;

	@GetMapping("")
	public String listUsers(Principal principal, final Model model) {

		model.addAttribute("usr", "active");
		model.addAttribute("items", userRepo.findAll());
		model.addAttribute("add", "add");

		return "controls/user-list";
	}

	@GetMapping("/add")
	public String addUser(Principal principal, final Model model,
			@RequestParam(name = "success", required = false) String success) {

		if (!syncRepo.findById(CONSTANTS.LICENSE_ID).isPresent()) {
			model.addAttribute("activated", "0");
			return "home";
		}

		sync_table synctable = syncRepo.findById(CONSTANTS.LICENSE_ID).get();
		model.addAttribute("myf", synctable.getSync_name());

		model.addAttribute("selected", new user_table());

		model.addAttribute("usr", "active");
		model.addAttribute("user_groups", groupRepo.findAll());
		model.addAttribute("user_facilities", facRepo.findAll());

		if (success != null) {
			model.addAttribute("success", "Saved successfully");
		}

		return "controls/user-create";
	}

	@PostMapping("/add")
	public String addUser(Principal principal, @ModelAttribute("selected") user_table selected, BindingResult result) {

		boolean userExists = userRepo.findById(selected.getUsername()).isPresent();
		boolean emailExists = userRepo.findByUser_email(selected.getUseremail()).isPresent();

		if (userExists) {
			result.rejectValue("username", "user.exists");
		}
		if (emailExists) {
			result.rejectValue("useremail", "email.exists");
		}

		if (result.hasErrors()) {
			return "controls/user-create";
		}

		String password = selected.getPassword();
		String encodedPassword = bCrypt.encode(password);
		selected.setPassword(encodedPassword);

		userRepo.save(selected);

		return "redirect:/controls/users/add?success=yes";
	}

	@GetMapping("/{id}")
	public String editUser(Principal principal, final Model model, @PathVariable("id") String user_id,
			@RequestParam(name = "success", required = false) String success) {

		user_table user = userRepo.findById(user_id).get();

		model.addAttribute("selected", user);
		model.addAttribute("usr", "active");
		model.addAttribute("user_groups", groupRepo.findAll());
		model.addAttribute("user_facilities", facRepo.findAll());

		if (success != null) {
			model.addAttribute("success", "Saved successfully");
		}

		return "controls/user-update";
	}

	@PostMapping("/{id}")
	public String editUser(Principal principal, @ModelAttribute user_table selected) {

		userRepo.save(selected);

		return "redirect:/controls/users/{id}?success=yes";
	}

	@GetMapping("/{id}/password")
	public String editUserPassword(Principal principal, final Model model, @PathVariable("id") String user_id,
			@RequestParam(name = "success", required = false) String success) {

		user_table user = userRepo.findById(user_id).get();

		model.addAttribute("selected", user);
		model.addAttribute("usr", "active");

		if (user_id.equals(user.getUsername()))
			model.addAttribute("currentuser", "currentuser");

		if (success != null) {
			model.addAttribute("success", "Saved successfully");
		}

		return "controls/user-update-password";
	}

	@PostMapping("/{id}/password")
	public String editUserPassword(Principal principal, @ModelAttribute("selected") user_table selected,
			BindingResult results) {

		if (selected.getUsername() == principal.getName()) {

			user_table check = userRepo.findById(selected.getUsername()).get();

			if (selected.getCur_password() != check.getPassword()) {
				results.rejectValue("current_password", "wrong.password", "Current password is incorrect");
			}

			if (results.hasErrors()) {
				return "controls/user-update-password";
			}

		}

		userRepo.save(selected);

		return "redirect:/controls/users/{id}/password?success=1";

	}

}// end class