package ru.kondratyeva.springdataproject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kondratyeva.springdataproject.models.Book;
import ru.kondratyeva.springdataproject.models.Person;
import ru.kondratyeva.springdataproject.services.BooksService;
import ru.kondratyeva.springdataproject.services.PeopleService;
import ru.kondratyeva.springdataproject.utill.PersonValidator;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Neil Alishev
 */
@Controller
@RequestMapping("/people")
public class PeopleController {


    private final PersonValidator personValidator;


    private final BooksService booksService;
    private final PeopleService peopleService;
    @Autowired
    public PeopleController(PersonValidator personValidator, BooksService booksService, PeopleService peopleService) {
        this.personValidator = personValidator;
        this.booksService = booksService;

        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        List<Book> books = peopleService.findAllBooks(id);
        model.addAttribute("books", books);
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/new";

        peopleService.save(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update( @PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                          BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        peopleService.update(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        peopleService.delete(id);
        return "redirect:/people";
    }


}
