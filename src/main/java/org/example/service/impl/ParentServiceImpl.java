package org.example.service.impl;


import org.example.dto.ParentDTO;
import org.example.entity.Parent;
import org.example.repository.ParentRepository;
import org.example.service.ParentService;
import org.example.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final MapperUtil mapperUtil;

    public ParentServiceImpl(ParentRepository parentRepository, MapperUtil mapperUtil) {
        this.parentRepository = parentRepository;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<ParentDTO> findAll() {
        return parentRepository.findAll()
                .stream()
                .map(parent -> mapperUtil.convert(parent, new ParentDTO()))
                .collect(Collectors.toList());
    }

    @Override
    public ParentDTO findById(Long id) throws Exception {
        Parent foundParent = parentRepository.findById(id)
                .orElseThrow(() -> new Exception("No Parent Found!"));
        return mapperUtil.convert(foundParent, new ParentDTO());
    }

}