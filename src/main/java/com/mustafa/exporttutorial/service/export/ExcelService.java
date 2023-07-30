package com.mustafa.exporttutorial.service.export;

import com.mustafa.exporttutorial.dto.UserDTO;
import com.mustafa.exporttutorial.model.MongoDataModel;
import com.mustafa.exporttutorial.service.MongoDataService;
import com.mustafa.exporttutorial.service.export.base.BaseService;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ExcelService extends BaseService {

    protected ExcelService(MongoDataService mongoDataService) {
        super(mongoDataService);
    }

    public ByteArrayInputStream exportToExcel() throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
//        Map<String, CellStyle> styles = createStyles(workbook);
        Sheet sheet = workbook.createSheet(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
        sheet.createRow(0); // Header Row
        sheet.createFreezePane(0, 1);

        CreationHelper creationHelper = workbook.getCreationHelper();

        XSSFFont hyperLinkFont = workbook.createFont();
        XSSFCellStyle hyperLinkStyle = workbook.createCellStyle();

        XSSFCellStyle dateStyle = workbook.createCellStyle();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd.mm.yyyy"));

        XSSFCellStyle timeStyle = workbook.createCellStyle();
        timeStyle.setDataFormat(creationHelper.createDataFormat().getFormat("HH:mm:ss"));

        Row row;
        int rowNum = 1;
        for (MongoDataModel mongoDataModel :
                mongoDataService.readAllData()) {
            UserDTO userDTO = mongoDataModel.getUserDTO();
            row = sheet.createRow(rowNum);
            Integer index = -1;

            // With Link
            XSSFHyperlink link = (XSSFHyperlink) creationHelper.createHyperlink(HyperlinkType.URL);
            link.setAddress("https://www.linkedin.com/in/mustafakarakas61/");
            hyperLinkFont.setUnderline((byte) 1);
            hyperLinkFont.setColor(IndexedColors.BLUE.getIndex());
            hyperLinkStyle.setFont(hyperLinkFont);
            Cell cellWithLink = row.createCell(index += 1);
            cellWithLink.setCellValue(row.getRowNum());
            cellWithLink.setHyperlink(link);
            cellWithLink.setCellStyle(hyperLinkStyle);

            row.createCell(index += 1, CellType.STRING).setCellValue(userDTO.getName());
            row.createCell(index += 1, CellType.STRING).setCellValue(userDTO.getSurname());
            row.createCell(index += 1, CellType.NUMERIC).setCellValue(userDTO.getAge());
            row.createCell(index += 1, CellType.STRING).setCellValue(userDTO.getCity());

            // Date
            Cell dateCell = row.createCell(index += 1, CellType.STRING);
            dateCell.setCellValue(userDTO.getBirthday());
            dateCell.setCellStyle(dateStyle);

            // Time
            Cell timeCell = row.createCell(index += 1, CellType.STRING);
            timeCell.setCellValue(LocalTime.now().toString());
            timeCell.setCellStyle(timeStyle);

            rowNum++;
        }

        for(int i = 0; i < titles.length; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        workbook.write(output);

        return new ByteArrayInputStream(output.toByteArray());
    }

    // Style Examples
    private static Map<String, CellStyle> createStyles(Workbook workbook) {
        Map<String, CellStyle> styles = new HashMap<>();
        DataFormat df = workbook.createDataFormat();
        CellStyle style;
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        styles.put("header", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setFont(headerFont);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("header_date", style);
        Font font1 = workbook.createFont();
        font1.setBold(true);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font1);
        styles.put("cell_b", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font1);
        styles.put("cell_b_centered", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_b_date", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_g", style);
        Font font2 = workbook.createFont();
        font2.setColor(IndexedColors.BLUE.getIndex());
        font2.setBold(true);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font2);
        styles.put("cell_bb", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setFont(font1);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_bg", style);
        Font font3 = workbook.createFont();
        font3.setFontHeightInPoints((short) 14);
        font3.setColor(IndexedColors.DARK_BLUE.getIndex());
        font3.setBold(true);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setFont(font3);
        //style.setWrapText(true);
        styles.put("cell_h", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.LEFT);
        //style.setWrapText(true);
        styles.put("cell_normal", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.CENTER);
        //style.setWrapText(true);
        styles.put("cell_normal_centered", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.RIGHT);
        //style.setWrapText(true);
        style.setDataFormat(df.getFormat("d-mmm"));
        styles.put("cell_normal_date", style);
        style = createBorderedStyle(workbook);
        style.setAlignment(HorizontalAlignment.LEFT);
        style.setIndention((short) 1);
        //style.setWrapText(true);
        styles.put("cell_indented", style);
        style = createBorderedStyle(workbook);
        style.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        styles.put("cell_blue", style);
        return styles;
    }
    private static CellStyle createBorderedStyle(Workbook workbook) {
        BorderStyle thin = BorderStyle.THIN;
        short black = IndexedColors.BLACK.getIndex();
        CellStyle style = workbook.createCellStyle();
        style.setBorderRight(thin);
        style.setRightBorderColor(black);
        style.setBorderBottom(thin);
        style.setBottomBorderColor(black);
        style.setBorderLeft(thin);
        style.setLeftBorderColor(black);
        style.setBorderTop(thin);
        style.setTopBorderColor(black);
        return style;
    }
}
