package org.pdsr;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class CONSTANTS {

	public static final String LICENSE_ID = "MPDRSGHANA";
	public static final String LICENSE_COUNTRY = "Ghana";
	public static final String WEBADMIN_ID = "webadmin";
	public static final String ROOT_LOCATION = "attachments";
	public static final String IMAGE_TAG = "XZYimageXZY";

	// DHIS2 Program UIDs
	public static final String DHIS2_MATERNAL_NOTIFICATION_PROGRAM = "ZJRDIb1joXP";
	public static final String DHIS2_PERINATAL_NOTIFICATION_PROGRAM = "vic5CfG1122";
	public static final String DHIS2_MATERNAL_REVIEW_PROGRAM = "ucCZOBcEqma";
	public static final String DHIS2_PERINATAL_REVIEW_PROGRAM = "k8dOLgiWpiI";
	public static final String DHIS2_FORM_STATUS = "COMPLETED";

	// DHIS2 Attribute Option Combos
	public static final String DHIS2_ATTRIBUTECOMBO_NATIONAL = "Lf2Axb9E6B4";
	public static final String DHIS2_ATTRIBUTECOMBO_FOREIGNER = "HqSHzyweG3W";
	public static final String DHIS2_ATTRIBUTECOMBO_REFUGEE = "TFRceXDkJ95";

	// DHIS2 Nationality Codes
	public static final Integer DHIS2_NATIONAL = 1, DHIS2_REFUGEE = 2, DHIS2_FOREIGNER = 3;

	// DHIS2 Maternal Death - Where Died
	public static final String DHIS2_DIED_UNDELIVERED = "Undelivered";
	public static final String DHIS2_DIED_INLABOUR = "In Labour";
	public static final String DHIS2_DIED_AFTERDELIVERY = "After Delivery";
	public static final String DHIS2_DIED_INTHEATRE = "In Theatre";

	// DHIS2 Perinatal Death Types
	public static final String DHIS2_FSB = "FSB (Fresh Stillbirth)";
	public static final String DHIS2_MSB = "MSB (Macerated Stillbirth)";
	public static final String DHIS2_END = "Neonatal Death (0-7 days)";

	// public static final String BASE_URL_GHANA =
	// "https://olincgroup.com/pdsr/ghana";
	// public static final String BASE_URL_NAMIBIA =
	// "https://olincgroup.com/pdsr/namibia";
	// P final String BASE_URL = "https://olincgroup.com/pdsr/sierraleone";

	public static final Integer STILL_BIRTH = 1, NEONATAL_DEATH = 2, MATERNAL_DEATH = 3;

	public static final Integer ARCHIVED_CASE = 9;

	public static final void writeToDisk(final String USER, final String MEMO, String filename, byte bytes[])
			throws IOException {
		Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
		Files.createDirectories(path.getParent(), new FileAttribute[0]);
		Files.write(path, bytes, new OpenOption[0]);
	}

	public static final byte[] readFromDisk(final String USER, final String MEMO, String filename) throws IOException {
		Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
		return Files.readAllBytes(path);
	}

	public static final byte[] readICD10(String filename) throws IOException {
		// Resource resource = new ClassPathResource("static/" + filename);
		// Path path = Paths.get(resource.getURI());
		// Path path = Paths.get(filename);
		return Files.readAllBytes(Paths.get(filename));
	}

	public static final byte[] read(String folder, String filename) throws IOException {
		// Resource resource = new ClassPathResource("static/" + filename);
		// Path path = Paths.get(resource.getURI());
		// Path path = Paths.get(filename);
		return Files.readAllBytes(Paths.get(folder).resolve(filename));
	}

	public static final boolean removeFromDisk(final String USER, final String MEMO, String filename)
			throws IOException {
		Path path = Paths.get(ROOT_LOCATION, new String[0]).resolve(USER).resolve(MEMO).resolve(filename);
		return Files.deleteIfExists(path);
	}

	public static boolean isWindows() {
		return OS.contains("win");
	}

	public static boolean isMac() {
		return OS.contains("mac");
	}

	public static boolean isUnix() {
		return OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0;
	}

	public static boolean isSolaris() {
		return OS.contains("sunos");
	}

	private static final String OS = System.getProperty("os.name").toLowerCase();

	public static long calculateAgeInDays(Date startDate, Date endDate) {
		// Convert java.util.Date to LocalDate
		LocalDate localStartDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		// Calculate the difference in days
		return ChronoUnit.DAYS.between(localStartDate, localEndDate);
	}

}