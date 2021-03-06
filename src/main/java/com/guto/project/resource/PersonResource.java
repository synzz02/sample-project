package com.guto.project.resource;

import com.guto.project.domain.dto.PersonPersistDTO;
import com.guto.project.domain.entity.Person;
import com.guto.project.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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

    @PostMapping
    public Person post(@RequestBody PersonPersistDTO personPersistDTO) {
        Person personCreated = personService.save(personPersistDTO);
        if (personCreated == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect body for POST request");
        }
        return personCreated;
    }

    @PutMapping("/{id}")
    public Person put(@PathVariable("id") Integer id, @RequestBody PersonPersistDTO personPersistDTO) {
        Person personUpdated = personService.update(id, personPersistDTO);
        if (personUpdated == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Person not found by ID or incorrect body for PUT request");
        }
        return personUpdated;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        personService.delete(id);
    }

}
