package org.pdsr;

public class Utils {

	public static final String MEMBER = "MEMBERS";
	public static final String CUSTOMER = "CUSTOMERS";
	public static final String[] MONTHS = new String[] { "SELECT MONTH", "JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY",
			"JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER" };

	public static final int[][] PRIORITY_MATRIX = new int[][] { { 2, 1 }, { 1, 2 }, { 2, 1 }, { 1, 2 } };
	public static final int EXPECTED_CASES_PER_WEEK = 3;
	//index 0 is the last week base on the Chinese remainder
}
