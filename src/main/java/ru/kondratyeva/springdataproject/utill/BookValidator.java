package ru.kondratyeva.springdataproject.utill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kondratyeva.springdataproject.models.Book;
import ru.kondratyeva.springdataproject.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;
    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
       Book book=(Book) o;
       Book result = booksService.findByName(book.getName());
       if(result!=null && result.getId()!=book.getId()){
           errors.rejectValue("name","","Книга с таким названием уже существует"+
                   book.getId()+" "+result.getId());
       }
    }
}
