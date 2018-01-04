package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.Attribute;
import com.github.schmittjoaopedro.domain.AttributeValue;
import com.github.schmittjoaopedro.repository.AttributeRepository;
import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class AttributeService {

    @Resource
    private AttributeRepository attributeRepository;

    @Resource
    private Session session;

    @Transactional(readOnly = true)
    public Attribute findOne(Long id) {
        return attributeRepository.findById(id).get();
    }

    @Transactional
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Transactional
    public void deleteAll() {
        for(Attribute attribute : attributeRepository.findAll()) {
            session.delete(attribute.getDescriptions());
            for(AttributeValue attributeValue : attribute.getValues()) {
                session.delete(session.load(AttributeValue.class, attributeValue.getId()).getDescriptions());
            }
            session.delete(attribute.getValues());
        }
        attributeRepository.deleteAll();
    }

}
