package com.guto.project.service;

import com.guto.project.domain.dto.PersonPersistDTO;
import com.guto.project.domain.entity.Person;
import com.guto.project.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public Person findByName(String name) {
        return personRepository.findByName(name);
    }

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person save(PersonPersistDTO personPersistDTO) {
        PersonPersistDTO persistDTO = new PersonPersistDTO(personPersistDTO.getName(), personPersistDTO.getAge());
        if (persistDTO.validate()) {
            personRepository.save(new Person(personPersistDTO.getName(), personPersistDTO.getAge()));
            return this.findByName(persistDTO.getName());
        }
        return null;
    }

    public Person update(Integer id, PersonPersistDTO personPersistDTO) {
        Optional<Person> person = personRepository.findById(id);
        if (person.isPresent() && personPersistDTO.validate()) {
            person.get().setName(personPersistDTO.getName());
            person.get().setAge(personPersistDTO.getAge());
            personRepository.save(person.get());
            return person.get();
        }
        return null;
    }

}
