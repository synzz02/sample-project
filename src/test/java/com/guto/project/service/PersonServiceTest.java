package com.guto.project.service;

import com.guto.project.domain.entity.Person;
import com.guto.project.repository.PersonRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonService personService;

    @After
    public void after() {
        personRepository.deleteAll();
    }

    @Test
    public void shouldFindByName() {
        Person person = new Person("testing", 18);
        personRepository.save(person);

        Person personResult = personService.findByName("testing");

        assertEquals(person.getId(), personResult.getId());
    }

    @Test
    public void shouldNotFindByNameBecauseNameIsIncorrectOrPersonDoesNotExist() {
        assertNull(personService.findByName("Golden Guto"));
    }
}
