package ru.kondratyeva.springdataproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kondratyeva.springdataproject.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    public Person findPersonByFullName(String fullName);
}
