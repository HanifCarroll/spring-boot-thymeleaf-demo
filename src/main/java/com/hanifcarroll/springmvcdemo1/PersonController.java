package com.hanifcarroll.springmvcdemo1;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/people")
public class PersonController {

    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {

        this.personRepository = personRepository;
    }

    @GetMapping({"", "/"})
    public String index(Model model) {

        model.addAttribute("people", personRepository.findAll());
        return "people/index";
    }

    @GetMapping({"/new", "/new/"})
    public String newPerson(Model model) {

        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping({"", "/"})
    public String savePerson(Person person) {

        personRepository.save(person);
        return "redirect:/people    ";
    }

    @GetMapping({"/{id}", "/{id}/"})
    public String showPerson(Model model, @PathVariable("id") Long id) {

        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        model.addAttribute("person", person);

        return "people/show";
    }

    @PutMapping({"/{id}", "/{id}/"})
    public String updatePerson(Person person, @PathVariable("id") Long id) {

        Person personToUpdate = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        personToUpdate.setFirstName(person.firstName);
        personToUpdate.setLastName(person.lastName);

        personRepository.save(personToUpdate);

        return "redirect:/people/{id}";
    }

    @DeleteMapping({"/{id}", "/{id}/"})
    public String deletePerson(@PathVariable("id") Long id) {

        personRepository.deleteById(id);
        return "redirect:/people";
    }

    @GetMapping({"/{id}/edit", "/{id}/edit/"})
    public String editPerson(Model model, @PathVariable("id") Long id) {

        Person person = personRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        model.addAttribute("person", person);

        return "people/edit";
    }

}
