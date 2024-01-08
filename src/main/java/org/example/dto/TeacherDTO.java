package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.enums.EducationLevel;
import org.example.enums.Status;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String phoneNumber;

    private String email;
    private String username;
    private String password;

    private LocalDate birthday;

    private Status status;

    private EducationLevel educationLevel;

    private AddressDTO address;

}