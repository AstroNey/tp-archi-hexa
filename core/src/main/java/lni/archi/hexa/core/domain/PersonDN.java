package lni.archi.hexa.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDN extends Domain {

    private  String firstName;
    private String name;
    private int age;

    public PersonDN(Integer id, String firstName, String name, int age) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.age = age;
    }
}
