package org.pdsr.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.pdsr.model.user_table;
import org.pdsr.repo.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
	private UserTableRepository userRepo;

	@GetMapping("")
	public String listUsers(Principal principal, final Model model) {

		model.addAttribute("is_users", "active");
		model.addAttribute("items", userRepo.findAll());

		return "controls/user-list";
	}

	@GetMapping("/add")
	public String addUser(Principal principal, final Model model,
			@RequestParam(name = "success", required = false) String success) {

		model.addAttribute("selected", new user_table());
		model.addAttribute("is_users", "active");

		if (success != null) {
			model.addAttribute("success", "Saved successfully");
		}

		return "controls/user-create";
	}

	@PostMapping("/add")
	public String addUser(Principal principal, @ModelAttribute user_table selected) {

		userRepo.save(selected);

		return "redirect:/controls/users/add?success=yes";
	}

	@GetMapping("/{id}")
	public String editUser(Principal principal, final Model model, @PathVariable("id") String user_id,
			@RequestParam(name = "success", required = false) String success) {

		user_table user = userRepo.findById(user_id).get();

		model.addAttribute("selected", user);
		model.addAttribute("is_users", "active");

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
		model.addAttribute("is_users", "active");

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

	@ModelAttribute("role_options")
	public Map<String, String> roleOptionsSelectOne() {
		final Map<String, String> map = new LinkedHashMap<>();

		map.put("ROLE_ADMIN", "");
		map.put("ROLE_ENTRY", "");
		map.put("ROLE_AUDIT", "");
		map.put("ROLE_TASKS", "Change Status of Action to be taken");
		map.put("ROLE_VIEWS", "View reports and statistics summaries");

		return map;
	}

}// end class