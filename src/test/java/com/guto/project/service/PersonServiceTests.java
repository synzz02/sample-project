package com.guto.project.service;

import com.guto.project.domain.entity.Person;
import com.guto.project.repository.PersonRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTests {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    private Person person;

    @Before
    public void before() {
        personRepository.deleteAll();
        person = new Person("testing", 18);
        personRepository.save(person);
    }

    @After
    public void after() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldFindByName() {
        Person personResult = personService.findByName("testing");

        assertEquals(this.person.getId(), personResult.getId());
    }

    @Test
    public void shouldNotFindByNameBecauseNameIsIncorrectOrPersonDoesNotExist() {
        assertNull(personService.findByName("Golden Guto"));
    }

    @Test
    public void shouldFindAll() {
        Person person = new Person("testing 2", 18);
        personRepository.save(person);

        List<Person> listOfPersons = new ArrayList<>();
        listOfPersons.add(this.person);
        listOfPersons.add(person);

        List<Person> listOfPersonsResult = personService.findAll();

        assertEquals(listOfPersons.toString(), listOfPersonsResult.toString());
    }
}
