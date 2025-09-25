package lni.archi.hexa.core.model;

import lni.archi.hexa.core.domain.PersonDN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonML extends DomainML {

    private String name;
    private  String firstName;
    private int age;

    public PersonML(Integer id, String name, String firstName, int age) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }

    public PersonML(PersonDN person) {
        this.id = person.getId();
        this.name = person.getName();
        this.firstName = person.getFirstName();
        this.age = person.getAge();
    }
}
