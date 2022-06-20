package com.guto.project.service;

import com.guto.project.domain.entity.Person;
import com.guto.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findByName(String name) {
        return personRepository.findByName(name);
    }
}
