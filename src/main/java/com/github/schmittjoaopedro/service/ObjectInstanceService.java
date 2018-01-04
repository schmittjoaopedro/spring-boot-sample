package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.ObjectInstance;
import com.github.schmittjoaopedro.domain.ObjectValue;
import com.github.schmittjoaopedro.repository.ObjectInstanceRepository;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class ObjectInstanceService {

    @Resource
    private ObjectInstanceRepository objectInstanceRepository;

    @Resource
    private Session session;

    @Transactional
    public void deleteAll() {
        for(ObjectInstance objectInstance : objectInstanceRepository.findAll()) {
            session.delete(objectInstance.getValues());
        }
        objectInstanceRepository.deleteAll();
    }

    @Transactional
    public ObjectInstance save(ObjectInstance objectInstance) {
        return objectInstanceRepository.save(objectInstance);
    }

    @Transactional(readOnly = true)
    public ObjectInstance findOne(Long id) {
        return objectInstanceRepository.findById(id).get();
    }
}
