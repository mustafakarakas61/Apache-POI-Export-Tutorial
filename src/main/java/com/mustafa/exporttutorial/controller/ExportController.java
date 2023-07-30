package com.mustafa.exporttutorial.controller;

import com.itextpdf.text.DocumentException;
import com.mustafa.exporttutorial.dto.UserDTO;
import com.mustafa.exporttutorial.entity.UserEntity;
import com.mustafa.exporttutorial.model.MongoDataModel;
import com.mustafa.exporttutorial.service.MongoDataService;
import com.mustafa.exporttutorial.service.export.ExcelService;
import com.mustafa.exporttutorial.service.UserService;
import com.mustafa.exporttutorial.service.export.PDFService;
import com.mustafa.exporttutorial.service.export.PowerPointService;
import com.mustafa.exporttutorial.service.export.WordService;
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
    private final MongoDataService mongoDataService;

    private final ExcelService excelService;
    private final PowerPointService powerPointService;
    private final WordService wordService;
    private final PDFService pdfService;


    public ExportController(UserService userService, MongoDataService mongoDataService, ExcelService excelService, PowerPointService powerPointService, WordService wordService, PDFService pdfService) {
        this.userService = userService;
        this.mongoDataService = mongoDataService;
        this.excelService = excelService;
        this.powerPointService = powerPointService;
        this.wordService = wordService;
        this.pdfService = pdfService;
    }

    @GetMapping(value = "/excel", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportExcel() throws IOException {

        for(UserDTO userDTO : userService.readUserList()) {
            MongoDataModel mongoDataModel = new MongoDataModel();
            mongoDataModel.setUserDTO(userDTO);
            mongoDataService.createData(mongoDataModel);
        }
        mongoDataService.deleteAllData();

        ByteArrayInputStream excelData = excelService.exportToExcel();

        byte[] excelBytes = IOUtils.toByteArray(excelData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_list.xlsx");

        return new ResponseEntity<>(excelBytes, headers, 200);
    }

    @GetMapping(value = "/powerpoint", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToPowerPoint() throws IOException {
        ByteArrayInputStream pptData = powerPointService.exportToPowerPoint(userService.readUserList());

        byte[] pptBytes = IOUtils.toByteArray(pptData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_list.pptx");

        return new ResponseEntity<>(pptBytes, headers, 200);
    }

    @GetMapping(value = "/word", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportToWord() throws IOException {
        ByteArrayInputStream wordData = wordService.exportToWord(userService.readUserList());

        byte[] wordBytes = IOUtils.toByteArray(wordData);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "user_list.docx");

        return new ResponseEntity<>(wordBytes, headers, 200);
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
