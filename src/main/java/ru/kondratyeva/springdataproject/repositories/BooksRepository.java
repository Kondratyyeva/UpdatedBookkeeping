package ru.kondratyeva.springdataproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kondratyeva.springdataproject.models.Book;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book,Integer> {
    public List<Book> findByNameStartingWith(String startingLine);
    public Book findByName(String name);

}
