package org.pdsr.pojos;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pdsr.master.model.case_biodata;
import org.pdsr.master.model.case_identifiers;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    public static List<case_identifiers> returnCaseIds(InputStream is) {
        List<case_identifiers> caseids = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(CASEIDSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                case_identifiers caseid = new case_identifiers();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                caseid.setCase_date(currentCell.getLocalDateTimeCellValue().toLocalDate());
                            }
                            break;
                        case 1:
                            final String facility = (currentCell.toString());
                            
                            break;
                        case 2:
                            caseid.setCaseIdNumber(currentCell.toString());
                            break;
                        case 3:
                            caseid.setCaseType(currentCell.toString());
                            break;
                        case 4:
                            caseid.setMothersIdNo(currentCell.toString());
                            break;
                        case 5:
                            caseid.setMothersName(currentCell.toString());
                            break;
                        default:
                            break;
                    }
                    cellIdx++;
                }
                caseids.add(caseid);
            }
        } catch (IOException e) {
            throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
        }
        return caseids;
    }

    public static List<case_biodata> returnBiodata(InputStream is){
        List<Biodata> caseBiodata = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(CASEBIODATASHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Biodata biodata = new Biodata();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            biodata.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            biodata.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            String childSex = currentCell.toString();
                            int childSexCode = EntityMappings.BIODATA_CHILD_SEX.getOrDefault(childSex, null);
                            biodata.setChildSex(childSexCode);
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                biodata.setMothersAge((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 4:
                            String mothersEducation = currentCell.toString();
                            int mothersEducationCode = EntityMappings.BIODATA_MOTHER_EDUCATION.getOrDefault(mothersEducation, null);
                            biodata.setMotherEducation(mothersEducationCode);
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

    public static List<Referral> returnReferral(InputStream is) {
        List<Referral> caseReferrals = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(REFERRALSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Referral referral = new Referral();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            referral.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            referral.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            String wasReferred = currentCell.toString();
                            int wasReferredCode = EntityMappings.REFERRAL_WAS_REFERRED.getOrDefault(wasReferred, null);
                            referral.setWasReferred(wasReferredCode);
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

    public static List<Pregnancy> returnPregnancy(InputStream is) {
        List<Pregnancy> casePregnancies = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(PREGNANCYSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Pregnancy pregnancy = new Pregnancy();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            pregnancy.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            pregnancy.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                pregnancy.setPregnancyWeeks((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                pregnancy.setPregnancyDays((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 4:
                            String pregnancyType = currentCell.toString();
                            int pregnancyTypeCode = EntityMappings.PREGNANCY_TYPE.getOrDefault(pregnancyType, null);
                            pregnancy.setPregnancyType(pregnancyTypeCode);
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

    public static List<Antenatal> returnAntenatals(InputStream is) {
        List<Antenatal> antenatals = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(ANTENATALSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Antenatal antenatal = new Antenatal();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            antenatal.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            antenatal.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                antenatal.setAntenatalGravida((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                antenatal.setAntenatalParity((int) currentCell.getNumericCellValue());
                            }
                            break;
                        case 4:
                            String antenatalAnc = currentCell.toString();
                            int antenatalAncCode = EntityMappings.ANTENATAL_ANC.getOrDefault(antenatalAnc, null);
                            antenatal.setAntenatalAnc(antenatalAncCode);
                            break;
                        case 5:
                            String antenatalRisk = currentCell.toString();
                            int antenatalRiskCode = EntityMappings.ANTENATAL_RISK.getOrDefault(antenatalRisk, null);
                            antenatal.setAntenatalRisk(antenatalRiskCode);
                            break;
                        case 6:
                            String antenatalMotherHiv = currentCell.toString();
                            int antenatalMotherHivCode = EntityMappings.ANTENATAL_MOTHER_HIV.getOrDefault(antenatalMotherHiv, null);
                            antenatal.setAntenatalMotherHiv(antenatalMotherHivCode);
                            break;
                        case 7:
                            String antenatalUseOfAlcohol = currentCell.toString();
                            int antenatalUseOfAlcoholCode = EntityMappings.ANTENATAL_USE_OF_ALCOHOL.getOrDefault(antenatalUseOfAlcohol, null);
                            antenatal.setAntenatalUseOfAlcohol(antenatalUseOfAlcoholCode);
                            break;
                        case 8:
                            String antenatalExposureCigarette = currentCell.toString();
                            int antenatalExposureCigaretteCode = EntityMappings.ANTENATAL_EXPOSURE_CIGARETTES.getOrDefault(antenatalExposureCigarette, null);
                            antenatal.setAntenatalExposureCigarette(antenatalExposureCigaretteCode);
                            break;
                        case 9:
                            String antenatalUseOfHerbs = currentCell.toString();
                            int antenatalUseOfHerbsCode = EntityMappings.ANTENATAL_USE_OF_HERBS.getOrDefault(antenatalUseOfHerbs, null);
                            antenatal.setAntenatalUseOfHerbs(antenatalUseOfHerbsCode);
                            break;
                        case 10:
                            String antenatalIntakeFolicAcid = currentCell.toString();
                            int antenatalIntakeFolicAcidCode = EntityMappings.ANTENATAL_USE_OF_HERBS.getOrDefault(antenatalIntakeFolicAcid, null);
                            antenatal.setAntenatalIntakeFolicAcid(antenatalIntakeFolicAcidCode);
                            break;
                        case 11:
                            String antenatalTetanus = currentCell.toString();
                            int antenatalTetanusCode = EntityMappings.ANTENATAL_TETANUS.getOrDefault(antenatalTetanus, null);
                            antenatal.setAntenatalTetanus(antenatalTetanusCode);
                            break;
                        case 12:
                            String antenatalMalaria = currentCell.toString();
                            int antenatalMalariaCode = EntityMappings.ANTENATAL_MALARIA.getOrDefault(antenatalMalaria, null);
                            antenatal.setAntenatalMalaria(antenatalMalariaCode);
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

    public static List<Labour> returnLabour(InputStream is) {
        List<Labour> caseLabours = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(LABOURSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Labour labour = new Labour();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            labour.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            labour.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                labour.setLabourSeeDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                labour.setLabourSeeTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
                            }
                            break;
                        case 4:
                            String labourPeriod = currentCell.toString();
                            int labourPeriodCode = EntityMappings.LABOUR_STAFF_PERIOD.getOrDefault(labourPeriod, null);
                            labour.setLabourStaffPeriod(labourPeriodCode);
                            break;
                        case 5:
                            String motherHerbalSubstance = currentCell.toString();
                            int motherHerbalSubstanceCode = EntityMappings.MOTHER_HERBAL_SUBSTANCE.getOrDefault(motherHerbalSubstance, null);
                            labour.setMotherHerbalSubstance(motherHerbalSubstanceCode);
                            break;
                        case 6:
                            String labourStart = currentCell.toString();
                            int labourStartCode = EntityMappings.LABOUR_START.getOrDefault(labourStart, null);
                            labour.setLabourStart(labourStartCode);
                            break;
                        case 7:
                            String labourComplications = currentCell.toString();
                            int labourComplicationsCode = EntityMappings.LABOUR_COMPLICATIONS.getOrDefault(labourComplications, null);
                            labour.setLabourComplications(labourComplicationsCode);
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

    public static List<Delivery> returnDelivery(InputStream is) {
        List<Delivery> deliveries = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(DELIVERYSHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Delivery delivery = new Delivery();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            delivery.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            delivery.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                delivery.setDeliveryDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
                            }
                            break;
                        case 3:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                delivery.setDeliveryTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
                            }
                            break;
                        case 4:
                            String deliveryPeriod = currentCell.toString();
                            int deliveryPeriodCode = EntityMappings.DELIVERY_PERIOD.getOrDefault(deliveryPeriod, null);
                            delivery.setDeliveryPeriod(deliveryPeriodCode);
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                delivery.setBabyWeight(currentCell.getNumericCellValue());
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

    public static List<Birth> returnBirth(InputStream is) {
        List<Birth> births = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(BIRTH_SHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                Birth birth = new Birth();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            birth.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            birth.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            String modeOfDelivery = currentCell.toString();
                            int modeOfDeliveryCode = EntityMappings.MODE_OF_DELIVERY.getOrDefault(modeOfDelivery, null);
                            birth.setModeOfDelivery(modeOfDeliveryCode);
                            break;
                        case 3:
                            String vaginalDeliveryOccur = currentCell.toString();
                            int vaginalDeliveryOccurCode = EntityMappings.VAGINAL_DELIVERY_OCCUR.getOrDefault(vaginalDeliveryOccur, null);
                            birth.setVaginalDeliveryOccur(vaginalDeliveryOccurCode);
                            break;
                        case 4:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                birth.setCsDate(currentCell.getLocalDateTimeCellValue().toLocalDate());
                            }
                            break;
                        case 5:
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                birth.setCsTime(currentCell.getLocalDateTimeCellValue().toLocalTime());
                            }
                            break;
                        case 6:
                            String deliveredBy = currentCell.toString();
                            int deliveredByCode = EntityMappings.DELIVERED_BY.getOrDefault(deliveredBy, null);
                            birth.setDeliveredBy(deliveredByCode);
                            break;
                        case 7:
                            String deliveredIn = currentCell.toString();
                            int deliveredInCode = EntityMappings.DELIVERED_IN.getOrDefault(deliveredIn, null);
                            birth.setDeliveredBy(deliveredInCode);
                            break;
                        case 8:
                            String babyAbnormalities = currentCell.toString();
                            int babyAbnormalitiesCode = EntityMappings.BABY_ABNORMALITIES.getOrDefault(babyAbnormalities, null);
                            birth.setBabyAbnormalities(babyAbnormalitiesCode);
                            break;
                        case 9:
                            String cordProblems = currentCell.toString();
                            int cordProblemsCode = EntityMappings.CORD_PROBLEMS.getOrDefault(cordProblems, null);
                            birth.setBabyAbnormalities(cordProblemsCode);
                            break;
                        case 10:
                            String placentaProblems = currentCell.toString();
                            int placentaProblemsCode = EntityMappings.PLACENTA_PROBLEMS.getOrDefault(placentaProblems, null);
                            birth.setPlacentaProblems(placentaProblemsCode);
                            break;
                        case 11:
                            String liquorVolume = currentCell.toString();
                            int liquorVolumeCode = EntityMappings.LIQUOR_VOLUME.getOrDefault(liquorVolume, null);
                            birth.setLiquorVolume(liquorVolumeCode);
                            break;
                        case 12:
                            String liquorColor = currentCell.toString();
                            int liquorColorCode = EntityMappings.LIQUOR_COLOR.getOrDefault(liquorColor, null);
                            birth.setLiquorColor(liquorColorCode);
                            break;
                        case 13:
                            String liquorOdour = currentCell.toString();
                            int liquorOdourCode = EntityMappings.LIQUOR_ODOUR.getOrDefault(liquorOdour, null);
                            birth.setLiquorOdour(liquorOdourCode);
                            break;
                        case 14:
                            String stateOfBaby = currentCell.toString();
                            int stateOfBabyCode = EntityMappings.STATE_OF_BABY.getOrDefault(stateOfBaby, null);
                            birth.setStateOfBaby(stateOfBabyCode);
                            break;
                        case 15:
                            String maternalOutcome = currentCell.toString();
                            int maternalOutcomeCode = EntityMappings.MATERNAL_OUTCOME.getOrDefault(maternalOutcome, null);
                            birth.setMaternalOutcome(maternalOutcomeCode);
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

    public static List<FetalHeart> returnFetalHearts(InputStream is) {
        List<FetalHeart> fetalHearts = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(is)) {
            Sheet sheet = workbook.getSheet(FETALHEART_SHEET);
            Iterator<Row> rows = sheet.iterator();
            int rowNumber = 0;
            while (rows.hasNext()) {
                Row currentRow = rows.next();
                if (rowNumber == 0) {
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                FetalHeart fetalHeart = new FetalHeart();
                int cellIdx = 0;
                while (cellsInRow.hasNext()) {
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx) {
                        case 0:
                            fetalHeart.setMothersIdNo(currentCell.toString());
                            break;
                        case 1:
                            fetalHeart.setMothersName(currentCell.toString());
                            break;
                        case 2:
                            String fetalSoundFacility = currentCell.toString();
                            int fetalSoundFacilityCode = EntityMappings.FETAL_SOUND_FACILITY.getOrDefault(fetalSoundFacility, null);
                            fetalHeart.setFetalHeartSoundPresentFromReferringFacility(fetalSoundFacilityCode);
                            break;
                        case 3:
                            String fetalSoundArrival = currentCell.toString();
                            int fetalSoundArrivalCode = EntityMappings.FETAL_SOUND_ARRIVAL.getOrDefault(fetalSoundArrival, null);
                            fetalHeart.setFetalHeartSoundPresentOnArrival(fetalSoundArrivalCode);
                            break;
                        case 4:
                            String fetalSoundPeriod = currentCell.toString();
                            int fetalSoundPeriodCode = EntityMappings.FETAL_SOUND_PERIOD.getOrDefault(fetalSoundPeriod, null);
                            fetalHeart.setFetalHeartSoundPeriod(fetalSoundPeriodCode);
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
