package com.github.schmittjoaopedro.service;

import com.github.schmittjoaopedro.domain.Person;
import com.github.schmittjoaopedro.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    @Resource
    private PersonRepository personRepository;

    @Transactional
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Transactional
    public void remove(Long id) {
        personRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        for(Person person : personRepository.findAll()) {
            result.add(person);
        }
        return result;
    }
}
