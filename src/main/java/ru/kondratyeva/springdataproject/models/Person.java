package ru.kondratyeva.springdataproject.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
@Entity
@Table(name = "person")
public class Person  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;
    @Column(name = "full_name")
    @NotNull(message = "Поле не должно быть пустым")
    private String fullName;
    @Min(value=0,message="Год должен быть больше 0")
    @Column(name = "year_of_birth")
    private int yearOfBirth;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    public Person() {

    }
    public Person(int user_id, String fullName, int yearOfBirth) {
        this.user_id = user_id;
        this.fullName = fullName;
        this.yearOfBirth = yearOfBirth;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

}
