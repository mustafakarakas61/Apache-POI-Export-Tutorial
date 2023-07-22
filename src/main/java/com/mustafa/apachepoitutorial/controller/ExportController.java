package com.mustafa.apachepoitutorial.controller;

import com.mustafa.apachepoitutorial.service.ExcelService;
import com.mustafa.apachepoitutorial.service.UserService;
import org.apache.poi.util.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/export/")
public class ExportController {

    private final UserService userService;
    private final ExcelService excelService;

    public ExportController(UserService userService, ExcelService excelService) {
        this.userService = userService;
        this.excelService = excelService;
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

}
