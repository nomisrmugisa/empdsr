package org.pdsr.enums;

import java.util.Arrays;
import java.util.HashMap;

public enum GroupRoles {

	
	ROLE_ENTRY("ROLE_ENTRY", "Enter cases into the system"),
	ROLE_AUDIT("ROLE_AUDIT", "Review and recommend actions on submitted cases"),
	ROLE_TASKS("ROLE_TASKS", "Monitor and change action status"),
	ROLE_SETUP("ROLE_SETUP", "Manage users, facility code from the controls section"),
	ROLE_VIEWS("ROLE_VIEWS", "View analysis and reports"),
	ROLE_NATIONAL("ROLE_NATIONAL", "National level viewing"),
	ROLE_REGIONAL("ROLE_REGIONAL", "Regional level viewing"),
	ROLE_DISTRICT("ROLE_DISTRICT", "District level viewing");

	private static HashMap<String, GroupRoles> enumById = new HashMap<>();
	static {
		Arrays.stream(values()).forEach(e -> enumById.put(e.getId(), e));
	}

	public static GroupRoles getById(String id) {
		return enumById.getOrDefault(id, ROLE_ENTRY);
	}

	private String id;
	private String description;

	private GroupRoles(String id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

}
