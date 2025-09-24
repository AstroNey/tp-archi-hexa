package lni.archi.hexa.core.model;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TeamMLF extends TeamML {

    private List<PersonML> persons;

    public TeamMLF(Integer id, String name, List<PersonDN> persons) {
        super(id, name);
        this.persons = convertPersons(persons);
    }

    public TeamMLF(TeamDN team) {
        this.id = team.getId();
        this.name = team.getName();
        if (team.getPersons() != null) {
            this.persons = convertPersons(team.getPersons());
        }
    }

    private List<PersonML> convertPersons(List<PersonDN> personsDN) {
        return personsDN.stream()
                .map(PersonML::new)
                .toList();
    }
}
