package org.pdsr.pojos;

import java.util.Map;

public class EntityMappings {

    // BIODATA MAPPINGS
    public static final Map<String, Integer> BIODATA_CHILD_SEX = Map.of(
            "Male", 1,
            "Female", 2,
            "Indeterminate", 3,
            "Unknown", 4,
            "Not Stated", 5
    );

    public static final Map<String, Integer> BIODATA_MOTHER_EDUCATION = Map.of(
            "No Education", 1,
            "Non-Formal", 2,
            "Basic/Primary", 3,
            "Secondary", 4,
            "Post Secondary/Vocation", 5,
            "Tertiary", 6,
            "Not Stated", 7
    );

    // REFERRAL MAPPINGS
    public static final Map<String, Integer> REFERRAL_WAS_REFERRED = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    // PREGNANCY MAPPINGS
    public static final Map<String, Integer> PREGNANCY_TYPE = Map.of(
            "Singleton", 1,
            "Twins", 2,
            "Triplets", 3,
            "Not Stated", 4,
            "Other", 5
    );

    // ANTENATAL MAPPINGS
    public static final Map<String, Integer> ANTENATAL_ANC = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_RISK = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_MOTHER_HIV = Map.of(
            "Negative(-ve)", 0,
            "Positive(+ve)", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_USE_OF_ALCOHOL = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_EXPOSURE_CIGARETTES= Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_USE_OF_HERBS = Map.of(
            "No", 1,
            "Yes", 2,
            "Unknown", 3,
            "Not Stated", 4
    );

    public static final Map<String, Integer> ANTENATAL_INTAKE_FOLIC_ACID = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_TETANUS = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> ANTENATAL_MALARIA = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    // LABOUR MAPPINGS
    public static final Map<String, Integer> LABOUR_STAFF_PERIOD = Map.of(
            "Morning Shift(08:00-13:59)", 1,
            "Afternoon Shift(14:00-19:59)", 3,
            "Night Shift(20:00-7:59)", 6
    );

    public static final Map<String, Integer> MOTHER_HERBAL_SUBSTANCE = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> LABOUR_START = Map.of(
            "No Labour", 0,
            "Spontaneous", 1,
            "Induced", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> LABOUR_COMPLICATIONS = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    // DELIVERY MAPPINGS
    public static final Map<String, Integer> DELIVERY_PERIOD = Map.of(
            "Morning Shift(08:00-13:59)", 1,
            "Afternoon Shift(14:00-19:59)", 3,
            "Night Shift(20:00-7:59)", 6
    );

    // BIRTH MAPPINGS
    public static final Map<String, Integer> MODE_OF_DELIVERY = Map.of(
            "Spontaneous Vaginal Delivery", 0,
            "Assisted Delivery(Vacuum/forceps)", 1,
            "Caesarian Section", 2,
            "Not Stated", 3
    );

    public static final Map<String, Integer> VAGINAL_DELIVERY_OCCUR = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> DELIVERED_BY = Map.ofEntries(
            Map.entry("Specialist", 0),
            Map.entry("Resident", 1),
            Map.entry("Medical Officer", 2),
            Map.entry("Medical/Physician Assistant", 3),
            Map.entry("House Officer", 4),
            Map.entry("Midwife", 5),
            Map.entry("Nurse(Registered General)", 6),
            Map.entry("Enrolled/Comm/Public Health Nurse", 7),
            Map.entry("Traditional Birth Attendant(TBA)", 8),
            Map.entry("Unknown", 9),
            Map.entry("Not Stated", 10)
    );

    public static final Map<String, Integer> DELIVERED_IN = Map.of(
            "Health Facility", 0,
            "Other", 66,
            "Unknown", 77,
            "Not Stated", 88
    );

    public static final Map<String, Integer> BABY_ABNORMALITIES = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> CORD_PROBLEMS = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> PLACENTA_PROBLEMS = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> LIQUOR_VOLUME = Map.of(
            "Adequate", 0,
            "Reduced(Oligohydramnious)", 1,
            "No fluid(Anhydramnious)", 2,
            "Too much fluid(Polyhydramnious)", 3,
            "Not Stated", 4
    );

    public static final Map<String, Integer> LIQUOR_COLOR = Map.of(
            "Clear", 0,
            "Meconium+", 1,
            "Meconium++", 2,
            "Meconium+++", 3,
            "Blood Stained", 4,
            "Other", 5,
            "Not Stated", 6
    );

    public static final Map<String, Integer> LIQUOR_ODOUR = Map.of(
            "Normal", 0,
            "Foul Smell", 1,
            "Not Stated", 2
    );

    public static final Map<String, Integer> STATE_OF_BABY = Map.of(
            "Fresh stillbirth", 0,
            "Macerated stillbirth", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> MATERNAL_OUTCOME = Map.of(
            "Alive", 0,
            "Dead", 1,
            "Unknown", 2,
            "Not Stated", 3
    );

    // FETAL HEART MAPPINGS
    public static final Map<String, Integer> FETAL_SOUND_FACILITY = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> FETAL_SOUND_ARRIVAL = Map.of(
            "No", 0,
            "Yes", 1,
            "Unknown", 2,
            "Not Stated", 3,
            "Not Applicable", 4
    );

    public static final Map<String, Integer> FETAL_SOUND_PERIOD = Map.of(
            "Antepartum", 0,
            "Intrapartum(first stage)", 1,
            "Intrapartum(second stage)", 2,
            "Unknown", 77,
            "Not Stated", 88
    );
}
