package org.example.service;



import org.example.dto.TeacherDTO;

import java.util.List;

public interface TeacherService {

    List<TeacherDTO> findAll();

    TeacherDTO findById(Long id) throws Exception;

    TeacherDTO createTeacher(TeacherDTO teacherDTO);

}