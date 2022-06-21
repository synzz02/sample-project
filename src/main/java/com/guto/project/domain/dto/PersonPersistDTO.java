package com.guto.project.domain.dto;

public class PersonPersistDTO {
    private String name;

    private Integer age;

    public PersonPersistDTO() {
    }

    public PersonPersistDTO(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public boolean validate() {
        boolean nameIsValid = this.name != null && !this.name.isBlank();
        boolean ageIsValid = this.age != null && this.age > 0;
        return nameIsValid && ageIsValid;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
