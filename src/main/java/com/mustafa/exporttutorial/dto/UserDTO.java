package com.mustafa.exporttutorial.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDTO {

    private String name;

    private String surname;

    private Integer age;

    private String city;

    private LocalDate birthday;
}
