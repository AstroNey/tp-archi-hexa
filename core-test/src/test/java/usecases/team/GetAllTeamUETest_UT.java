package usecases.team;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.usescases.team.GetAllTeamUE;
import lni.archi.hexa.data.jpa.repositories.TeamJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import usecases.UtilsTest;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetAllTeamUETest_UT {

    @Mock
    private TeamJpaRepo repo;

    private GetAllTeamUE useCase;

    private TeamDN team;

    private TeamDN team2;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.useCase = new GetAllTeamUE(this.repo);

        team = new TeamDN(1, "Team A", List.of(
                new PersonDN(1, "John", "Doe", 30),
                new PersonDN(2, "Jane", "Smith", 25),
                new PersonDN(3, "Alice", "Johnson", 28),
                new PersonDN(4, "Bob", "Brown", 35),
                new PersonDN(5, "Charlie", "Davis", 22),
                new PersonDN(6, "Eve", "Wilson", 27)
        ));

        team2 = new TeamDN(2, "Team B", List.of(
                new PersonDN(1, "John", "Doe", 30),
                new PersonDN(2, "Jane", "Smith", 25),
                new PersonDN(3, "Alice", "Johnson", 28),
                new PersonDN(4, "Bob", "Brown", 35),
                new PersonDN(5, "Charlie", "Davis", 22),
                new PersonDN(6, "Eve", "Wilson", 27)
        ));
    }

    @Test
    public void should_get_all_teams() {
        List<TeamDN> teams = List.of(team, team2);

        when(repo.getAllTeam()).thenReturn(teams);

        List<TeamDN> result = useCase.execute();
        assertEquals(teams, result);
        assertEquals(2, result.size());

        assertEquals(team, result.get(0));
        assertEquals(team.getId(), result.get(0).getId());
        assertEquals(team.getName(), result.get(0).getName());

        List<PersonDN> resultMembers = result.get(0).getPersons();
        List<PersonDN> expectedMembers = team.getPersons();
        assertEquals(expectedMembers.size(), resultMembers.size());
        for (int i = 0; i < expectedMembers.size(); i++) {
            UtilsTest.testPerson(expectedMembers.get(i), resultMembers.get(i));
        }

        assertEquals(team2, result.get(1));
        assertEquals(team2.getId(), result.get(1).getId());
        assertEquals(team2.getName(), result.get(1).getName());

        resultMembers = result.get(1).getPersons();
        expectedMembers = team2.getPersons();
        assertEquals(expectedMembers.size(), resultMembers.size());
        for (int i = 0; i < expectedMembers.size(); i++) {
            UtilsTest.testPerson(expectedMembers.get(i), resultMembers.get(i));
        }

        verify(repo).getAllTeam();
    }
}
