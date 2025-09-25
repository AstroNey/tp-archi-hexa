package lni.archi.hexa.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonDN extends Domain {

    private String name;
    private  String firstName;
    private int age;

    public PersonDN(Integer id, String name,String firstName, int age) {
        this.id = id;
        this.firstName = firstName;
        this.name = name;
        this.age = age;
    }
}
