package org.pdsr.pojos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pdsr.master.model.case_antenatal;
import org.pdsr.master.model.case_biodata;
import org.pdsr.master.model.case_birth;
import org.pdsr.master.model.case_delivery;
import org.pdsr.master.model.case_fetalheart;
import org.pdsr.master.model.case_identifiers;
import org.pdsr.master.model.case_labour;
import org.pdsr.master.model.case_pregnancy;
import org.pdsr.master.model.case_referral;
import org.springframework.web.multipart.MultipartFile;

public class ExcelHelper {
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String CASEIDSHEET = "CaseIdentifiers";
    static String CASEBIODATASHEET = "CaseBiodata";
    static String REFERRALSHEET = "Referrals";
    static String PREGNANCYSHEET = "Pregnancy";
    static String ANTENATALSHEET = "Antenatal";
    static String LABOURSHEET = "Labour";
    static String DELIVERYSHEET = "Delivery";
    static String BIRTH_SHEET = "Birth";
    static String FETALHEART_SHEET = "FetalHeart";
    //static String NOTES_SHEET = "Notes";

    public static boolean hasExcelFormat(MultipartFile file) {

        if (!TYPE.equals(file.getContentType())) {
            return false;
        }

        return true;
    }


	public List<case_biodata> returnBiodata(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_biodata> caseBiodata = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.CASEBIODATASHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_biodata biodata = new case_biodata();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						biodata.setCase_uuid(caseMap.get(case_id_num));
						biodata.setBiodata_uuid(biodata.getCase_uuid().getCase_uuid());
						break;
					case 2:
						final String childSex = currentCell.toString();
						final int childSexCode = EntityMappings.BIODATA_CHILD_SEX.getOrDefault(childSex, null);
						biodata.setBiodata_sex(childSexCode);
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							biodata.setBiodata_mage((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						final String mothersEducation = currentCell.toString();
						final int mothersEducationCode = EntityMappings.BIODATA_MOTHER_EDUCATION
								.getOrDefault(mothersEducation, null);
						biodata.setBiodata_medu(mothersEducationCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseBiodata.add(biodata);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseBiodata;
	}

	public List<case_referral> returnReferral(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_referral> caseReferrals = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.REFERRALSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_referral referral = new case_referral();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						referral.setReferral_uuid(caseMap.get(case_id_num).getCase_uuid());
						break;
					case 2:
						final String wasReferred = currentCell.toString();
						final int wasReferredCode = EntityMappings.REFERRAL_WAS_REFERRED.getOrDefault(wasReferred,
								null);
						referral.setReferral_case(wasReferredCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseReferrals.add(referral);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseReferrals;
	}

	public List<case_pregnancy> returnPregnancy(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_pregnancy> casePregnancies = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.PREGNANCYSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_pregnancy pregnancy = new case_pregnancy();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						pregnancy.setPregnancy_uuid(caseMap.get(case_id_num).getCase_uuid());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							pregnancy.setPregnancy_weeks((int) currentCell.getNumericCellValue());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							pregnancy.setPregnancy_days((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						String pregnancyType = currentCell.toString();
						int pregnancyTypeCode = EntityMappings.PREGNANCY_TYPE.getOrDefault(pregnancyType, null);
						pregnancy.setPregnancy_type(pregnancyTypeCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				casePregnancies.add(pregnancy);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return casePregnancies;
	}

	public List<case_antenatal> returnAntenatals(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_antenatal> antenatals = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.ANTENATALSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_antenatal antenatal = new case_antenatal();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						antenatal.setAntenatal_uuid(caseMap.get(case_id_num).getCase_uuid());
						antenatal.setCase_uuid(caseMap.get(case_id_num));
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							antenatal.setAntenatal_gravida((int) currentCell.getNumericCellValue());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							antenatal.setAntenatal_para((int) currentCell.getNumericCellValue());
						}
						break;
					case 4:
						String antenatalAnc = currentCell.toString();
						int antenatalAncCode = EntityMappings.ANTENATAL_ANC.getOrDefault(antenatalAnc, null);
						antenatal.setAntenatal_attend(antenatalAncCode);
						break;
					case 5:
						String antenatalRisk = currentCell.toString();
						int antenatalRiskCode = EntityMappings.ANTENATAL_RISK.getOrDefault(antenatalRisk, null);
						antenatal.setAntenatal_risks(antenatalRiskCode);
						break;
					case 6:
						String antenatalMotherHiv = currentCell.toString();
						int antenatalMotherHivCode = EntityMappings.ANTENATAL_MOTHER_HIV
								.getOrDefault(antenatalMotherHiv, null);
						antenatal.setAntenatal_hiv(antenatalMotherHivCode);
						break;
					case 7:
						String antenatalUseOfAlcohol = currentCell.toString();
						int antenatalUseOfAlcoholCode = EntityMappings.ANTENATAL_USE_OF_ALCOHOL
								.getOrDefault(antenatalUseOfAlcohol, null);
						antenatal.setAntenatal_alcohol(antenatalUseOfAlcoholCode);
						break;
					case 8:
						String antenatalExposureCigarette = currentCell.toString();
						int antenatalExposureCigaretteCode = EntityMappings.ANTENATAL_EXPOSURE_CIGARETTES
								.getOrDefault(antenatalExposureCigarette, null);
						antenatal.setAntenatal_smoker(antenatalExposureCigaretteCode);
						break;
					case 9:
						String antenatalUseOfHerbs = currentCell.toString();
						int antenatalUseOfHerbsCode = EntityMappings.ANTENATAL_USE_OF_HERBS
								.getOrDefault(antenatalUseOfHerbs, null);
						antenatal.setAntenatal_herbal(antenatalUseOfHerbsCode);
						break;
					case 10:
						String antenatalIntakeFolicAcid = currentCell.toString();
						int antenatalIntakeFolicAcidCode = EntityMappings.ANTENATAL_USE_OF_HERBS
								.getOrDefault(antenatalIntakeFolicAcid, null);
						antenatal.setAntenatal_folicacid(antenatalIntakeFolicAcidCode);
						break;
					case 11:
						String antenatalTetanus = currentCell.toString();
						int antenatalTetanusCode = EntityMappings.ANTENATAL_TETANUS.getOrDefault(antenatalTetanus,
								null);
						antenatal.setAntenatal_tetanus(antenatalTetanusCode);
						break;
					case 12:
						String antenatalMalaria = currentCell.toString();
						int antenatalMalariaCode = EntityMappings.ANTENATAL_MALARIA.getOrDefault(antenatalMalaria,
								null);
						antenatal.setAntenatal_malprophy(antenatalMalariaCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				antenatals.add(antenatal);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return antenatals;
	}

	public List<case_labour> returnLabour(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_labour> caseLabours = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.LABOURSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_labour labour = new case_labour();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						labour.setCase_uuid(caseMap.get(case_id_num));
						labour.setLabour_uuid(labour.getCase_uuid().getCase_uuid());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							labour.setLabour_seedate(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							labour.setLabour_seetime(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 4:
						String labourPeriod = currentCell.toString();
						int labourPeriodCode = EntityMappings.LABOUR_STAFF_PERIOD.getOrDefault(labourPeriod, null);
						labour.setLabour_seeperiod(labourPeriodCode);
						break;
					case 5:
						String motherHerbalSubstance = currentCell.toString();
						int motherHerbalSubstanceCode = EntityMappings.MOTHER_HERBAL_SUBSTANCE
								.getOrDefault(motherHerbalSubstance, null);
						labour.setLabour_herbalaug(motherHerbalSubstanceCode);
						break;
					case 6:
						String labourStart = currentCell.toString();
						int labourStartCode = EntityMappings.LABOUR_START.getOrDefault(labourStart, null);
						labour.setLabour_startmode(labourStartCode);
						break;
					case 7:
						String labourComplications = currentCell.toString();
						int labourComplicationsCode = EntityMappings.LABOUR_COMPLICATIONS
								.getOrDefault(labourComplications, null);
						labour.setLabour_complications(labourComplicationsCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				caseLabours.add(labour);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return caseLabours;
	}

	public List<case_delivery> returnDelivery(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_delivery> deliveries = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.DELIVERYSHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_delivery delivery = new case_delivery();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						delivery.setCase_uuid(caseMap.get(case_id_num));
						delivery.setDelivery_uuid(delivery.getCase_uuid().getCase_uuid());
						break;
					case 2:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setDelivery_date(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 3:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setDelivery_time(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 4:
						String deliveryPeriod = currentCell.toString();
						int deliveryPeriodCode = EntityMappings.DELIVERY_PERIOD.getOrDefault(deliveryPeriod, null);
						delivery.setDelivery_period(deliveryPeriodCode);
						break;
					case 5:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							delivery.setDelivery_weight(currentCell.getNumericCellValue());
						}
						break;
					default:
						break;
					}
					cellIdx++;
				}
				deliveries.add(delivery);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return deliveries;
	}

	public List<case_birth> returnBirth(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_birth> births = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.BIRTH_SHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_birth birth = new case_birth();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					case 0:
						final String case_id_num = (currentCell.toString());
						birth.setCase_uuid(caseMap.get(case_id_num));
						birth.setBirth_uuid(birth.getCase_uuid().getCase_uuid());
						break;
					case 2:
						String modeOfDelivery = currentCell.toString();
						int modeOfDeliveryCode = EntityMappings.MODE_OF_DELIVERY.getOrDefault(modeOfDelivery, null);
						birth.setBirth_mode(modeOfDeliveryCode);
						break;
					case 3:
						String birth_insistnormal = currentCell.toString();
						int birth_insistnormalCode = EntityMappings.VAGINAL_DELIVERY_OCCUR
								.getOrDefault(birth_insistnormal, null);
						birth.setBirth_insistnormal(birth_insistnormalCode);
						break;
					case 4:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							birth.setBirth_csproposedate(currentCell.getLocalDateTimeCellValue().toLocalDate());
						}
						break;
					case 5:
						if (currentCell.getCellType() == CellType.NUMERIC) {
							birth.setBirth_csproposetime(currentCell.getLocalDateTimeCellValue().toLocalTime());
						}
						break;
					case 6:
						String deliveredBy = currentCell.toString();
						int deliveredByCode = EntityMappings.DELIVERED_BY.getOrDefault(deliveredBy, null);
						birth.setBirth_provider(deliveredByCode);
						break;
					case 7:
						String deliveredIn = currentCell.toString();
						int deliveredInCode = EntityMappings.DELIVERED_IN.getOrDefault(deliveredIn, null);
						birth.setBirth_facility(deliveredInCode);
						break;
					case 8:
						String babyAbnormalities = currentCell.toString();
						int babyAbnormalitiesCode = EntityMappings.BABY_ABNORMALITIES.getOrDefault(babyAbnormalities,
								null);
						birth.setBirth_abnormalities(babyAbnormalitiesCode);
						break;
					case 9:
						String cordProblems = currentCell.toString();
						int cordProblemsCode = EntityMappings.CORD_PROBLEMS.getOrDefault(cordProblems, null);
						birth.setBirth_cordfaults(cordProblemsCode);
						break;
					case 10:
						String placentaProblems = currentCell.toString();
						int placentaProblemsCode = EntityMappings.PLACENTA_PROBLEMS.getOrDefault(placentaProblems,
								null);
						birth.setBirth_placentachecks(placentaProblemsCode);
						break;
					case 11:
						String liquorVolume = currentCell.toString();
						int liquorVolumeCode = EntityMappings.LIQUOR_VOLUME.getOrDefault(liquorVolume, null);
						birth.setBirth_liqourvolume(liquorVolumeCode);
						break;
					case 12:
						String liquorColor = currentCell.toString();
						int liquorColorCode = EntityMappings.LIQUOR_COLOR.getOrDefault(liquorColor, null);
						birth.setBirth_liqourcolor(liquorColorCode);
						break;
					case 13:
						String liquorOdour = currentCell.toString();
						int liquorOdourCode = EntityMappings.LIQUOR_ODOUR.getOrDefault(liquorOdour, null);
						birth.setBirth_liqourodour(liquorOdourCode);
						break;
					case 14:
						String stateOfBaby = currentCell.toString();
						int stateOfBabyCode = EntityMappings.STATE_OF_BABY.getOrDefault(stateOfBaby, null);
						birth.setBirth_babyoutcome(stateOfBabyCode);
						break;
					case 15:
						String maternalOutcome = currentCell.toString();
						int maternalOutcomeCode = EntityMappings.MATERNAL_OUTCOME.getOrDefault(maternalOutcome, null);
						birth.setBirth_motheroutcome(maternalOutcomeCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				births.add(birth);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return births;
	}

	public List<case_fetalheart> returnFetalHearts(InputStream is, Map<String, case_identifiers> caseMap) {
		List<case_fetalheart> fetalHearts = new ArrayList<>();
		try (Workbook workbook = new XSSFWorkbook(is)) {
			Sheet sheet = workbook.getSheet(SheetSections.FETALHEART_SHEET.getDescription());
			Iterator<Row> rows = sheet.iterator();
			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}
				
				Iterator<Cell> cellsInRow = currentRow.iterator();
				case_fetalheart fetalHeart = new case_fetalheart();
				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();
					switch (cellIdx) {
					
					case 0:
						final String case_id_num = (currentCell.toString());
						fetalHeart.setCase_uuid(caseMap.get(case_id_num));
						fetalHeart.setFetalheart_uuid(fetalHeart.getCase_uuid().getCase_uuid());
						break;
						
					case 2:
						String fetalSoundFacility = currentCell.toString();
						int fetalSoundFacilityCode = EntityMappings.FETAL_SOUND_FACILITY
								.getOrDefault(fetalSoundFacility, null);
						fetalHeart.setFetalheart_refered(fetalSoundFacilityCode);
						break;
					case 3:
						String fetalSoundArrival = currentCell.toString();
						int fetalSoundArrivalCode = EntityMappings.FETAL_SOUND_ARRIVAL.getOrDefault(fetalSoundArrival,
								null);
						fetalHeart.setFetalheart_arrival(fetalSoundArrivalCode);
						break;
					case 4:
						String fetalSoundPeriod = currentCell.toString();
						int fetalSoundPeriodCode = EntityMappings.FETAL_SOUND_PERIOD.getOrDefault(fetalSoundPeriod,
								null);
						fetalHeart.setFetalheart_lastheard(fetalSoundPeriodCode);
						break;
					default:
						break;
					}
					cellIdx++;
				}
				fetalHearts.add(fetalHeart);
			}
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		}
		return fetalHearts;
	}

//    public static List<Notes> returnNotes(InputStream is) {
//        List<Notes> notesList = new ArrayList<>();
//        try (Workbook workbook = new XSSFWorkbook(is)) {
//            Sheet sheet = workbook.getSheet(NOTES_SHEET);
//            Iterator<Row> rows = sheet.iterator();
//            int rowNumber = 0;
//            while (rows.hasNext()) {
//                Row currentRow = rows.next();
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//                Iterator<Cell> cellsInRow = currentRow.iterator();
//                Notes notes = new Notes();
//                int cellIdx = 0;
//                while (cellsInRow.hasNext()) {
//                    Cell currentCell = cellsInRow.next();
//                    switch (cellIdx) {
//                        case 0:
//                            notes.setAdditionalNotes(currentCell.toString());
//                            break;
//                        default:
//                            break;
//                    }
//                    cellIdx++;
//                }
//                notesList.add(notes);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
//        }
//        return notesList;
//    }


}
