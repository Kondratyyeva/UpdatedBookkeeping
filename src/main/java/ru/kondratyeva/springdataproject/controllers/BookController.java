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
import ru.kondratyeva.springdataproject.utill.BookValidator;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookValidator bookValidator;
    private final BooksService booksService;
    private final PeopleService peopleService;
    @Autowired
    public BookController(BookValidator bookValidator, BooksService booksService, PeopleService peopleService) {
        this.bookValidator = bookValidator;
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(@RequestParam(value="page", required = false) Integer page,
                        @RequestParam(value="books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value="sort_by_year", required = false) boolean sortByYear,
                        Model model){
        if((page != null & booksPerPage != null & sortByYear)) {
            model.addAttribute("books", booksService.findAllPagingSortedByYear(page, booksPerPage));
        }else if(page!=null & booksPerPage!=null){
            model.addAttribute("books", booksService.findAllPaging(page, booksPerPage));
        }else if (sortByYear){
            model.addAttribute("books", booksService.findAllSortedByYear());
        }else{
            model.addAttribute("books", booksService.findAll());
        }
        return "/books/index";
    }
    @GetMapping("/search")
    public String search(){
        return "books/search";
    }
    @PatchMapping("/search")
    public String searchBook(@RequestParam(value="query",required = false ) String query, Model model){
        List<Book> books = booksService.findByNameStartingWith(query);
        model.addAttribute("books", booksService.findByNameStartingWith(query));
        return "books/search";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,@ModelAttribute("person") Person person, Model model){
        model.addAttribute("book", booksService.findById(id));
        Person bookOwner = booksService.getOwner(id);
        if(bookOwner !=null)
        {
            model.addAttribute("owner", bookOwner);
        }
        else model.addAttribute("people",peopleService.findAll());
        return "/books/show";
    }
    @GetMapping("/new")
    public String create(@ModelAttribute("book") Book book){
        return "/books/new";
    }
    @PostMapping()
    public String add(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if(bindingResult.hasErrors())
            return "redirect:/books/new";
        booksService.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book",booksService.findById(id));
        return "/books/edit";
    }
    @PatchMapping("/{id}")
    public String update (@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/edit";
        booksService.update(id,book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }
    @PatchMapping("/{id}/addOwner")
    public String addOwner(@PathVariable("id") int id,@ModelAttribute("person") Person person){
         booksService.addOwner(id,person);
         return "redirect:/books/"+id;
    }
    @PatchMapping("/{id}/deleteOwner")
    public String deleteOwner(@PathVariable("id") int id){
        booksService.deleteOwner(id);
        return "redirect:/books/"+id;
    }
}
