package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.ClassType;
import com.github.schmittjoaopedro.repository.ClassTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ClassTypeService {

    @Resource
    private ClassTypeRepository classTypeRepository;

    @Transactional
    public ClassType save(ClassType classType) {
        return classTypeRepository.save(classType);
    }

    @Transactional
    public void deleteAll() {
        classTypeRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public ClassType findOne(Long id) {
        return classTypeRepository.findOne(id);
    }
}
