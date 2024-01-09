package org.example.controller;

import org.example.dto.TeacherDTO;
import org.example.service.TeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController

public class SchoolController {
    private final TeacherService teacherService;

    public SchoolController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/teachers")
    public List<TeacherDTO> getAllTeachers() {
        return teacherService.findAll();
    }
}
