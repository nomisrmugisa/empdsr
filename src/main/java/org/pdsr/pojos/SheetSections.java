package org.pdsr.pojos;

import java.util.Arrays;
import java.util.HashMap;

public enum SheetSections {

	CASEIDSHEET(0, "Case"), 
	CASEBIODATASHEET(1, "Biodata"), 
	REFERRALSHEET(2, "Referrals"), 
	PREGNANCYSHEET(3, "Pregnancy"),
	ANTENATALSHEET(4, "Antenatal"),
	LABOURSHEET(5, "Labour"),
	DELIVERYSHEET(6, "Delivery"),
	BIRTH_SHEET(7, "Birth"),
	FETALHEART_SHEET(8, "FetalHeart"),
	NOTES_SHEET(9, "Notes");

	private static HashMap<Integer, SheetSections> enumById = new HashMap<>();
	static {
		Arrays.stream(values()).forEach(e -> enumById.put(e.getId(), e));
	}

	public static SheetSections getById(Integer id) {
		return enumById.getOrDefault(id, CASEIDSHEET);
	}

	private Integer id;
	private String description;

	private SheetSections(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public Integer getId() {
		return id;
	}

}
