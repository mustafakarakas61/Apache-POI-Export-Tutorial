package com.mustafa.exporttutorial.service.export;

import com.mustafa.exporttutorial.dto.UserDTO;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class WordService {

    public ByteArrayInputStream exportToWord(List<UserDTO> userDTOs) throws IOException {
        XWPFDocument doc = new XWPFDocument();
        ByteArrayOutputStream wordData = new ByteArrayOutputStream();

        for (UserDTO userDTO : userDTOs) {
            XWPFParagraph para = doc.createParagraph();
            XWPFRun run = para.createRun();
            run.setText("Name: " + userDTO.getName() +
                    "\nSurname: " + userDTO.getSurname() +
                    "\nAge: " + userDTO.getAge() +
                    "\nCity: " + userDTO.getCity() +
                    "\nBirthday: " + userDTO.getBirthday());
            run.addBreak();
        }

        doc.write(wordData);
        return new ByteArrayInputStream(wordData.toByteArray());
    }
}
