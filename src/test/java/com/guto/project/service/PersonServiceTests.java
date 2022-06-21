package com.guto.project.service;

import com.guto.project.domain.dto.PersonPersistDTO;
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

    @Test
    public void shouldSavePerson() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO("Testing guto", 22);
        personService.save(personPersistDTO);

        Person person = personRepository.findByName(personPersistDTO.getName());

        assertEquals(personPersistDTO.getName(), person.getName());
    }

    @Test
    public void shouldNotSavePersonBecauseAgeIsNull() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setName("Testing guto um");

        assertNull(personService.save(personPersistDTO));
    }

    @Test
    public void shouldNotSavePersonBecauseNameIsNull() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setAge(18);

        assertNull(personService.save(personPersistDTO));
    }

    @Test
    public void shouldNotSavePersonBecauseNameIsBlank() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setAge(18);
        personPersistDTO.setName(" ");

        assertNull(personService.save(personPersistDTO));
    }

    @Test
    public void shouldNotSavePersonBecauseAgeIsZero() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setAge(0);
        personPersistDTO.setName("Testing guto three");

        assertNull(personService.save(personPersistDTO));
    }

    @Test
    public void shouldNotSavePersonBecauseAgeIsNegative() {
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setAge(-18);
        personPersistDTO.setName("Testing guto four");

        assertNull(personService.save(personPersistDTO));
    }

    @Test
    public void shouldUpdatePerson() {
        Integer id = this.person.getId();
        PersonPersistDTO personPersistDTO = new PersonPersistDTO("Testing guto five", 27);
        personService.update(id, personPersistDTO);

        Person person = personRepository.findByName(personPersistDTO.getName());

        assertEquals(this.person.getId(), person.getId());
        assertEquals(personPersistDTO.getName(), person.getName());
        assertEquals(personPersistDTO.getAge(), person.getAge());
    }

    @Test
    public void shouldNotUpdatePersonBecauseIdIsIncorrect() {
        Integer id = 999;
        PersonPersistDTO personPersistDTO = new PersonPersistDTO("Testing guto six", 28);

        assertNull(personService.update(id, personPersistDTO));
    }

    @Test
    public void shouldNotUpdatePersonBecauseBodyRequestIsIncorrect() {
        Integer id = this.person.getId();
        PersonPersistDTO personPersistDTO = new PersonPersistDTO();
        personPersistDTO.setName("Guto testing seven");

        assertNull(personService.update(id, personPersistDTO));
    }
}
