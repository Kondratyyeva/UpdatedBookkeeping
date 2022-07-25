package ru.kondratyeva.springdataproject.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kondratyeva.springdataproject.models.Book;
import ru.kondratyeva.springdataproject.repositories.PeopleRepository;
import ru.kondratyeva.springdataproject.models.Person;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;
    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }
    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    public Person findById(int id){
        Optional<Person> person = peopleRepository.findById(id);

        return person.orElse(null);
    }
    public Person findPersonByFullName(String fullName){
        return peopleRepository.findPersonByFullName(fullName);
    }
    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }
    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setUser_id(id);
        peopleRepository.save(updatedPerson);
    }
    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public List<Book> findAllBooks(int id){
        Optional<Person> person = peopleRepository.findById(id);
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            Date currentTime = new Date();
            for(Book book: person.get().getBooks()){
                long difference = currentTime.getTime()-book.getTookAt().getTime();
                int days =  (int)(difference / (24 * 60 * 60 * 1000));
                if(days>10){
                    book.setShouldBeReturned(true);
                }
            }
            return person.get().getBooks();
        } else{
            return Collections.emptyList();
        }
    }
}
