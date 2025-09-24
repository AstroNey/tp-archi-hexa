package lni.archi.hexa.core.model;

import lni.archi.hexa.core.domain.PersonDN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonML extends DomainML {

    private  String firstName;
    private String name;
    private int age;

    public PersonML(Integer id, String firstName, String name, int age) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.age = age;
    }

    public PersonML(PersonDN person) {
        this.id = person.getId();
        this.firstName = person.getFirstName();
        this.name = person.getName();
        this.age = person.getAge();
    }
}
