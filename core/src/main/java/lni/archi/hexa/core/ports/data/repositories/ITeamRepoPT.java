package lni.archi.hexa.core.ports.data.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;

import java.util.List;

public interface ITeamRepoPT {

    TeamDN createTeam(String name, List<PersonDN> persons);

    TeamDN getTeamById(Integer id);

    List<TeamDN> getAllTeam();
}
