package org.example.controller;

import org.example.dto.ResponseWrapper;
import org.example.dto.StudentDTO;
import org.example.dto.TeacherDTO;
import org.example.service.StudentService;
import org.example.service.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SchoolController {
    private final TeacherService teacherService;
    private final StudentService studentService;

    public SchoolController(TeacherService teacherService, StudentService studentService) {
        this.teacherService = teacherService;
        this.studentService = studentService;
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
}
