package lni.archi.hexa.hexarest.controllers;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.model.PersonML;
import lni.archi.hexa.hexarest.configs.cleanArchi.usescases.person.PersonUseCases;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = "*")
public class PersonController {

    private final PersonUseCases personUseCases;

    @Autowired
    public PersonController(PersonUseCases personUseCases) {
        this.personUseCases = personUseCases;
    }


    @PostMapping("/persons")
    public ResponseEntity<PersonML> createPerson(final @RequestBody PersonDN person) {
        try {
            PersonDN createdPerson = this.personUseCases.getCreatePersonUE()
                    .execute(person.getFirstName(), person.getName(), person.getAge());
            PersonML result = new PersonML(createdPerson);

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<PersonML>  getPersonById(@PathVariable String id) {
        try {
            PersonML result = new PersonML(this.personUseCases.getGetPersonByIdUE().execute(Integer.parseInt(id)));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/persons")
    public ResponseEntity<List<PersonML>> getAllPersons() {
        try {
            List<PersonML> result = new ArrayList<>();

            List<PersonDN> toTransform = this.personUseCases.getGetAllPersonUE().execute();
            for (PersonDN person : toTransform) {
                result.add(new PersonML(person));
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }
}
