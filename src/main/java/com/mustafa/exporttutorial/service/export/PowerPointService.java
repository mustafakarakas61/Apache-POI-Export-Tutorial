package com.mustafa.exporttutorial.service.export;

import com.mustafa.exporttutorial.dto.UserDTO;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFTextShape;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PowerPointService {

    public ByteArrayInputStream exportToPowerPoint(List<UserDTO> userDTOs) throws IOException {
        XMLSlideShow ppt = new XMLSlideShow();
        ByteArrayOutputStream pptData = new ByteArrayOutputStream();

        for (UserDTO userDTO : userDTOs) {
            XSLFSlide slide = ppt.createSlide();

            XSLFTextShape title = slide.createTextBox();
            title.setAnchor(new Rectangle2D.Double(100, 100, 600, 50));
            title.setText("User Information");
            title.setFillColor(Color.BLUE);

            XSLFTextShape content = slide.createTextBox();
            content.setAnchor(new Rectangle2D.Double(100, 200, 600, 300));
            content.setText("Name: " + userDTO.getName() +
                    "\nSurname: " + userDTO.getSurname() +
                    "\nAge: " + userDTO.getAge() +
                    "\nCity: " + userDTO.getCity() +
                    "\nBirthday: " + userDTO.getBirthday());

        }

        ppt.write(pptData);
        return new ByteArrayInputStream(pptData.toByteArray());
    }
}
