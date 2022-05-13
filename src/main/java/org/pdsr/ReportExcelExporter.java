package org.pdsr;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.pdsr.master.model.weekly_monitoring;
import org.pdsr.master.model.weekly_table;

//import com.itextpdf.text.Font;

public class ReportExcelExporter {

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private List<weekly_table> report;
	private String yearmonth;

	public ReportExcelExporter(List<weekly_table> report, String yearmonth) {
		this.report = report;
		this.yearmonth = yearmonth;
		workbook = new XSSFWorkbook();
	}

	private void writeHeaderLine() {
		sheet = workbook.createSheet(yearmonth);

		Row headerRow = sheet.createRow(0);

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.WHITE.getIndex());
		font.setFontHeight(16);
		headerStyle.setFont(font);

		int colIndex = 0;
		// serial numbers on the first column
		createCell(headerRow, colIndex++, "SN", headerStyle);

		// indicators on the second column
		createCell(headerRow, colIndex++, "Indicators -" + yearmonth, headerStyle);

		// week indices for the subsequent columns
		for (; colIndex <= report.size() + 1; colIndex++) {
			createCell(headerRow, colIndex, "Week " + (colIndex - 1), headerStyle);
		}

	}

	private void createCell(Row row, int colIndex, Object value, CellStyle style) {

		sheet.autoSizeColumn(colIndex);

		Cell cell = row.createCell(colIndex);

		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);

		} else if (value instanceof Double) {
			cell.setCellValue((Double) value);

		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);

		} else {
			cell.setCellValue((String) value);

		}

		cell.setCellStyle(style);
	}

	private void writeDataLines() {

		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		CellStyle tstyle = workbook.createCellStyle();
		XSSFFont tfont = workbook.createFont();
		tfont.setFontHeight(14);
		tfont.setBold(true);
		tfont.setColor(IndexedColors.DARK_BLUE.index);
		tstyle.setFont(tfont);

		weekly_table week1 = report.get(0);
		int rowIndex = 1;

		final int snColIndex = 0;// column for the serial numbers
		final int lbColIndex = 1;// column for the indicator labels

		int snIndexer = 1;// initialize the serial numbering process

		for (weekly_monitoring wm : week1.getStatistics()) {

			Row row = sheet.createRow(rowIndex++);
			boolean istotal = wm.getWm_indices().getMindex() % wm.getWm_indices().getGindex() == 0;
			boolean hashtml = wm.getWm_indices().getMindex() % 130 == 3 || wm.getWm_indices().getMindex() % 130 == 8
					|| wm.getWm_indices().getMindex() % 130 == 9;

			// insert the serial numbers at column 0
			createCell(row, snColIndex, snIndexer++, tstyle);

			// insert the indicator labels at column 1
			final String label;

			if (istotal) {
				label = wm.getWm_indices().getMlabel() + " (" + wm.getWm_indices().getMdesc() + ")";
				createCell(row, lbColIndex, label, tstyle);
			} else if (hashtml) {
				label = wm.getWm_indices().getMdesc();
				createCell(row, lbColIndex, label, style);

			} else {
				label = wm.getWm_indices().getMlabel();
				createCell(row, lbColIndex, label, style);

			}

		}

		int wkColIndex = 2;// starting column for the data
		for (weekly_table week : report) {
			rowIndex = 1;
			for (weekly_monitoring wm : week.getStatistics()) {
				Row row = sheet.getRow(rowIndex);
				boolean istotal = wm.getWm_indices().getMindex() % wm.getWm_indices().getGindex() == 0;
				boolean hashtml = wm.getWm_indices().getMindex() % 130 == 3 || wm.getWm_indices().getMindex() % 130 == 8
						|| wm.getWm_indices().getMindex() % 130 == 9;

				if (istotal) {
					createCell(row, wkColIndex, wm.getWm_values(), tstyle);
				} else if (hashtml) {
					createCell(row, wkColIndex, wm.getWm_values(), style);

				} else {
					createCell(row, wkColIndex, wm.getWm_values(), style);

				}
				rowIndex++;
			}

			wkColIndex++;
		}

	}

	public void export(HttpServletResponse response) throws IOException {
		writeHeaderLine();
		writeDataLines();

		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();

		outputStream.close();

	}

}
