package org.example.controller;

import org.example.dto.AddressDTO;
import org.example.dto.ResponseWrapper;
import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.entity.Address;
import org.example.service.AddressService;
import org.example.service.ParentService;
import org.example.service.StudentService;
import org.example.service.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class SchoolController {
    private final TeacherService teacherService;
    private final StudentService studentService;
    private final ParentService parentService;
    private final AddressService addressService;

    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.findAll();
    }

    @GetMapping("/students")
    public ResponseEntity<ResponseWrapper> getAllStudents() {
        List<StudentDTO> studentList = studentService.findAll();

        return ResponseEntity.ok(new ResponseWrapper("Students are retrieved successfully", studentList));
    }

    @GetMapping("/parents")
    public ResponseEntity<ResponseWrapper> getAllParents() {

        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Parents are successfully retrieved", HttpStatus.ACCEPTED.value(), parentService.findAll() );

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Parent","Returned")
                .body(responseWrapper);
    }

    @GetMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> getAddressById(@PathVariable("id") Long id) {
        AddressDTO addressDTO = null;
        try {
            addressDTO = addressService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new ResponseWrapper("Address is successfully retrieved", addressDTO ));

    }
}
