package com.guto.project.resource;

import com.guto.project.domain.entity.Person;
import com.guto.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/person")
public class PersonResource {

    @Autowired
    private PersonService personService;

    @GetMapping
    public Person getByName(@RequestParam String name) {
        return personService.findByName(name);
    }

    @GetMapping("/all")
    public List<Person> getAll() {
        return personService.findAll();
    }
}
