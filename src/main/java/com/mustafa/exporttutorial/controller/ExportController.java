package com.mustafa.exporttutorial.controller;

import com.itextpdf.text.DocumentException;
import com.mustafa.exporttutorial.service.export.ExcelService;
import com.mustafa.exporttutorial.service.UserService;
import com.mustafa.exporttutorial.service.export.PDFService;
import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/export/")
public class ExportController {

    private final UserService userService;

    private final ExcelService excelService;
    private final PDFService pdfService;

    public ExportController(UserService userService, ExcelService excelService, PDFService pdfService) {
        this.userService = userService;
        this.excelService = excelService;
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportExcel() throws IOException {
        ByteArrayInputStream excelData = excelService.exportToExcel(userService.readUserList());

        byte[] excelBytes = IOUtils.toByteArray(excelData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_list.xlsx");

        return new ResponseEntity<>(excelBytes, headers, 200);
    }

    @GetMapping(value = "/pdf", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToPDF() throws IOException, DocumentException {
        ByteArrayInputStream pdfData = pdfService.exportToPDF(userService.readUserList());

        byte[] pdfBytes = IOUtils.toByteArray(pdfData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_list.pdf");

        return new ResponseEntity<>(pdfBytes, headers, 200);
    }
}
