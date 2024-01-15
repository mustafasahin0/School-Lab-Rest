package org.example.service.impl;


import org.example.dto.StudentDTO;
import org.example.entity.Student;
import org.example.exception.NotFoundException;
import org.example.repository.StudentRepository;
import org.example.service.StudentService;
import org.example.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final MapperUtil mapperUtil;

    public StudentServiceImpl(StudentRepository studentRepository, MapperUtil mapperUtil) {
        this.studentRepository = studentRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll()
                .stream()
                .map(student -> mapperUtil.convert(student, new StudentDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO findById(Long id) throws Exception {
        Student foundStudent = studentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Student Found!"));
        return mapperUtil.convert(foundStudent, new StudentDTO());
    }

}