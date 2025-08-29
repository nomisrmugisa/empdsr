package org.pdsr.controller;

import java.util.List;

import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.repo.CaseRepository;
import org.pdsr.pojos.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/case")
public class CaseApiController {

	@Autowired
	private CaseRepository repo;

	@GetMapping("")
	public DataWrapper<case_identifiers> findAll() {

		List<case_identifiers> data = repo.findAll();

		DataWrapper<case_identifiers> w = new DataWrapper<>();
		w.setData(data);

		return w;
	}
}


