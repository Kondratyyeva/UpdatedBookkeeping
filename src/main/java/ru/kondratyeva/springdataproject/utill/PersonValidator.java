package ru.kondratyeva.springdataproject.utill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kondratyeva.springdataproject.models.Person;
import ru.kondratyeva.springdataproject.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;
    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
      Person person=(Person) o;
      Person result =peopleService.findPersonByFullName(person.getFullName());
      if(result!=null){
          errors.rejectValue("fullName","","Человек с таким ФИО уже существует"+result.getUser_id()+" "+person.getUser_id());
        }
    }
}
