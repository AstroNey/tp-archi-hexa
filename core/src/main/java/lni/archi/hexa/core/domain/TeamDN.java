package lni.archi.hexa.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamDN extends Domain {

    private String name;

    private List<PersonDN> persons;

    public TeamDN(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamDN(Integer id, String name, List<PersonDN> persons) {
        this.id = id;
        this.name = name;
        this.persons = persons;
    }
}
