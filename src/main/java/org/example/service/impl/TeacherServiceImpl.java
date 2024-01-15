package org.example.service.impl;


import org.example.dto.TeacherDTO;
import org.example.entity.Teacher;
import org.example.exception.NotFoundException;
import org.example.repository.TeacherRepository;
import org.example.service.TeacherService;
import org.example.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final MapperUtil mapperUtil;

    public TeacherServiceImpl(TeacherRepository teacherRepository, MapperUtil mapperUtil) {
        this.teacherRepository = teacherRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll()
                .stream()
                .map(teacher -> mapperUtil.convert(teacher, new TeacherDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public TeacherDTO findById(Long id) throws Exception {
        Teacher foundTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Teacher Found!"));
        return mapperUtil.convert(foundTeacher, new TeacherDTO());
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) {
        Teacher newTeacher = teacherRepository.save(mapperUtil.convert(teacherDTO, new Teacher()));
        return mapperUtil.convert(newTeacher, new TeacherDTO());
    }

}