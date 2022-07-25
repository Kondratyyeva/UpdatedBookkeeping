package ru.kondratyeva.springdataproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kondratyeva.springdataproject.models.Person;
import ru.kondratyeva.springdataproject.repositories.BooksRepository;

import ru.kondratyeva.springdataproject.models.Book;
import ru.kondratyeva.springdataproject.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;
    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }
    public List<Book> findAll(){
        return booksRepository.findAll();
    }
    public List<Book> findAllPaging(int page, int itemsPerPage){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage)).getContent();
    }
    public List<Book> findAllPagingSortedByYear(int page, int itemsPerPage){
        return booksRepository.findAll(PageRequest.of(page, itemsPerPage, Sort.by("year"))).getContent();
    }
    public List<Book> findAllSortedByYear(){
        return booksRepository.findAll(Sort.by("year"));
    }
    public Book findById(int id){
        Optional<Book> book =  booksRepository.findById(id);
        return book.orElse(null);
    }
    public Book findByName(String name){
        return booksRepository.findByName(name);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        Book book = booksRepository.findById(id).get();
        updatedBook.setId(id);//по сути добавляем новую книгу
        updatedBook.setOwner(book.getOwner());
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }
    @Transactional
    public void deleteOwner(int id){
        Optional<Book> book = booksRepository.findById(id);
        if(book.isPresent()){
            book.get().setOwner(null);
            book.get().setTookAt(null);
        }
    }

    public Person getOwner(int id){
        Optional<Book> book = booksRepository.findById(id);
        return book.map(Book::getOwner).orElse(null);
    }
    @Transactional
    public void addOwner(int id, Person person) {
        Optional<Book> book = booksRepository.findById(id);
        if(book.isPresent()){
            book.get().setOwner(person);
            book.get().setTookAt(new Date());
        }
    }

    public List<Book> findByNameStartingWith(String startingLine){
        return booksRepository.findByNameStartingWith(startingLine);
    }
}
