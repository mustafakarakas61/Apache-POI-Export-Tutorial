package com.mustafa.exporttutorial.service.export;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import com.itextpdf.text.*;
import com.mustafa.exporttutorial.dto.UserDTO;
import com.mustafa.exporttutorial.service.MongoDataService;
import com.mustafa.exporttutorial.service.export.base.BaseService;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFService extends BaseService {

    protected PDFService(MongoDataService mongoDataService) {
        super(mongoDataService);
    }

    public ByteArrayInputStream exportToPDF(List<UserDTO> userDTOs) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, output);
        document.open();

        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA);
        Font linkFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.UNDERLINE, BaseColor.BLUE);

        // Create a table with the same number of columns as the titles array
        PdfPTable table = new PdfPTable(titles.length);
        table.setWidthPercentage(100);

        // Add header row
        for (String title : titles) {
            PdfPCell headerCell = new PdfPCell(new Paragraph(title, headerFont));
            table.addCell(headerCell);
        }

        int rowNum = 1;
        for (UserDTO userDTO : userDTOs) {
            String linkedInProfile = "https://www.linkedin.com/in/mustafakarakas61/";
            Anchor link = new Anchor(linkedInProfile, linkFont);
            link.setReference(linkedInProfile);

            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase(String.valueOf(rowNum++), dataFont));
            paragraph.add(new Phrase(" "));
            paragraph.add(link);
            table.addCell(new PdfPCell(paragraph));

            // Add data rows
            table.addCell(new PdfPCell(new Paragraph(userDTO.getName(), dataFont)));
            table.addCell(new PdfPCell(new Paragraph(userDTO.getSurname(), dataFont)));
            table.addCell(new PdfPCell(new Paragraph(String.valueOf(userDTO.getAge()), dataFont)));
            table.addCell(new PdfPCell(new Paragraph(userDTO.getCity(), dataFont)));
            table.addCell(new PdfPCell(new Paragraph(userDTO.getBirthday().toString(), dataFont)));
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(output.toByteArray());
    }
}
