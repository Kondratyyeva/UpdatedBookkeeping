package ru.kondratyeva.springdataproject.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    @NotNull(message = "Поле не должно быть пустым")
    private String name;
    @Column(name = "author")
    @NotNull(message = "Поле не должно быть пустым")
    private String author;
    @Column(name = "year")
    @Min(value=0,message="Год должен быть больше 0")
    private int year;

    @Column(name = "took_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tookAt;

    @ManyToOne
    @JoinColumn(name= "user_id",referencedColumnName = "user_id")
    private Person owner;
    @Transient
    private Boolean isShouldBeReturned;

    public Book(){

    }

    public Book(int id, String name, String author, int year, Date tookAt) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
        this.tookAt=tookAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getTookAt() {
        return tookAt;
    }

    public void setTookAt(Date tookAt) {
        this.tookAt = tookAt;
    }

    public Boolean getShouldBeReturned() {
        return isShouldBeReturned;
    }

    public void setShouldBeReturned(Boolean shouldBeReturned) {
        isShouldBeReturned = shouldBeReturned;
    }
}
