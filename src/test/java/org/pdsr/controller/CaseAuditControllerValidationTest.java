package org.pdsr.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.pdsr.CONSTANTS;
import org.pdsr.master.model.case_babydeath;
import org.pdsr.master.model.case_birth;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.icd_codes;
import org.pdsr.master.repo.IcdCodesRepository;
import org.pdsr.pojos.icdpm;
import org.springframework.test.util.ReflectionTestUtils;

class CaseAuditControllerValidationTest {

	@Test
	void stillbirthAuditIcdOptionsComeFromBirthTabCodes() {
		CaseAuditController controller = new CaseAuditController();
		case_birth birth = new case_birth();
		birth.setBirth_cod_fetus_text("Birth asphyxia");
		birth.setBirth_cod_fetus_code("KA66");
		birth.setBirth_cod_maternal_text("Placental abruption");
		birth.setBirth_cod_maternal_code("JB63");
		case_identifiers selected = new case_identifiers();
		selected.setCase_death(CONSTANTS.STILL_BIRTH);
		selected.setBirth(birth);

		@SuppressWarnings("unchecked")
		Map<String, String> options = (Map<String, String>) ReflectionTestUtils.invokeMethod(
				controller, "buildAuditIcd11Options", selected);

		assertNotNull(options);
		assertEquals("(Fetus) Birth asphyxia", options.get("KA66"));
		assertEquals("(Maternal) Placental abruption", options.get("JB63"));
	}

	@Test
	void neonatalAuditIcdOptionsComeFromBabyDeathTabCodes() {
		CaseAuditController controller = new CaseAuditController();
		case_babydeath babydeath = new case_babydeath();
		babydeath.setBaby_cod_underlying("Neonatal sepsis");
		babydeath.setBaby_cod_underlying_code("1A40");
		babydeath.setBaby_cod_a("Respiratory failure");
		babydeath.setBaby_cod_a_code("CA40");
		case_identifiers selected = new case_identifiers();
		selected.setCase_death(CONSTANTS.NEONATAL_DEATH);
		selected.setBabydeath(babydeath);

		@SuppressWarnings("unchecked")
		Map<String, String> options = (Map<String, String>) ReflectionTestUtils.invokeMethod(
				controller, "buildAuditIcd11Options", selected);

		assertNotNull(options);
		assertEquals("(Underlying) Neonatal sepsis", options.get("1A40"));
		assertEquals("(a) Respiratory failure", options.get("CA40"));
		assertTrue(options.containsKey("1A40"));
	}

	@Test
	void pmSelectReturnsEmptyResultForLegacyNonCodeSelection() {
		CaseAuditController controller = new CaseAuditController();
		IcdCodesRepository icdRepo = mock(IcdCodesRepository.class);
		when(icdRepo.findICDByICD("legacy-value")).thenReturn(Optional.empty());
		ReflectionTestUtils.setField(controller, "icdRepo", icdRepo);

		icdpm result = controller.findPMCode(3, "legacy-value");

		assertNull(result.getPm_code());
		assertNull(result.getPm_desc());
	}

	@Test
	void pmSelectUsesResolvedIcdCodeForNeonatalAudit() {
		CaseAuditController controller = new CaseAuditController();
		IcdCodesRepository icdRepo = mock(IcdCodesRepository.class);
		icd_codes icd = new icd_codes();
		icd.setIcd_pmn("N9");
		icd.setIcd_pmn_desc("Neonatal condition");
		when(icdRepo.findICDByICD("CA40")).thenReturn(Optional.of(icd));
		ReflectionTestUtils.setField(controller, "icdRepo", icdRepo);

		icdpm result = controller.findPMCode(3, "CA40");

		assertEquals("N9", result.getPm_code());
		assertEquals("Neonatal condition", result.getPm_desc());
	}
}