package org.example.controller;

import org.example.client.WeatherClient;
import org.example.dto.*;
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
    private final WeatherClient weatherClient;


    public SchoolController(TeacherService teacherService, StudentService studentService, ParentService parentService, AddressService addressService, WeatherClient weatherClient) {
        this.teacherService = teacherService;
        this.studentService = studentService;
        this.parentService = parentService;
        this.addressService = addressService;
        this.weatherClient = weatherClient;
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

        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Parents are successfully retrieved", HttpStatus.ACCEPTED.value(), parentService.findAll());

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .header("Parent", "Returned")
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

        return ResponseEntity.ok(new ResponseWrapper("Address is successfully retrieved", addressDTO));

    }

    @PutMapping("/address/{id}")
    public ResponseEntity<ResponseWrapper> updateAddressById(@PathVariable("id") Long id, @RequestBody AddressDTO addressDTO) {
        AddressDTO currentAddressDTO = null;

        try {
            currentAddressDTO = addressService.findById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Long currentId = currentAddressDTO.getId();

        AddressDTO updatedAddressDTO = addressDTO;
        updatedAddressDTO.setId(currentId);

        try {
            addressService.update(updatedAddressDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(new ResponseWrapper("Address is successfully updated"));

    }

    @GetMapping("/address-weather/{id}")
    public ResponseEntity<ResponseWrapper> getAddressWithWeather(@PathVariable("id") Long id) throws Exception {

        AddressDTO addressDTO = addressService.findById(id);

        return ResponseEntity.ok(new ResponseWrapper("Address with temperature is successfully retrieved", addressDTO));

    }

    @PostMapping("/teachers")
    public ResponseEntity<ResponseWrapper> createTeacher(@RequestBody TeacherDTO teacherDTO) {
        TeacherDTO teacher = teacherService.createTeacher(teacherDTO);

        ResponseWrapper responseWrapper = new ResponseWrapper(true, "Teacher is created", HttpStatus.CREATED.value(), teacher);

        return ResponseEntity.status(HttpStatus.CREATED)
                .header("teacherId", String.valueOf(teacher.getId()))
                .body(responseWrapper);
    }
}
