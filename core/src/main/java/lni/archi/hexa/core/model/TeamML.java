package lni.archi.hexa.core.model;

import lni.archi.hexa.core.domain.TeamDN;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TeamML extends DomainML {
    protected String name;

    public TeamML(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TeamML(TeamDN team) {
        this.id = team.getId();
        this.name = team.getName();
    }
}
