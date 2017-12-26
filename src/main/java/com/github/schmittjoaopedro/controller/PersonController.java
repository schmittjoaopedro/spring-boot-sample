package com.github.schmittjoaopedro.controller;

import com.github.schmittjoaopedro.domain.Person;
import com.github.schmittjoaopedro.service.PersonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/resources/persons")
public class PersonController {

    @Resource
    private PersonService personService;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody
    Person save(Person person) {
        return personService.save(person);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Person> findAll() {
        return personService.findAll();
    }

}
