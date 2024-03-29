package org.example.service.impl;

import org.example.dto.CourseDTO;
import org.example.entity.Course;
import org.example.exception.NotFoundException;
import org.example.repository.CourseRepository;
import org.example.service.CourseService;
import org.example.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final MapperUtil mapperUtil;

    public CourseServiceImpl(CourseRepository courseRepository, MapperUtil mapperUtil) {
        this.courseRepository = courseRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<CourseDTO> findAll() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapperUtil.convert(course,new CourseDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDTO findById(Long id) throws Exception {
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Course Found!"));
        return mapperUtil.convert(foundCourse, new CourseDTO());
    }

}