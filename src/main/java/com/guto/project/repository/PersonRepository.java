package com.guto.project.repository;

import com.guto.project.domain.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByName(String name);
}
