package usecases.team;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.usescases.team.GetTeamByIdUE;
import lni.archi.hexa.data.jpa.repositories.TeamJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import usecases.UtilsTest;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetTeamByIdUETest_UT {

    @Mock
    private TeamJpaRepo repo;

    private GetTeamByIdUE usecase;

    private TeamDN team;

    @Before
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        this.usecase = new GetTeamByIdUE(this.repo);

        team = new TeamDN(1, "Team A", List.of(
                new PersonDN(1, "John", "Doe", 30),
                new PersonDN(2, "Jane", "Smith", 25),
                new PersonDN(3, "Alice", "Johnson", 28),
                new PersonDN(4, "Bob", "Brown", 35),
                new PersonDN(5, "Charlie", "Davis", 22),
                new PersonDN(6, "Eve", "Wilson", 27)
        ));
    }

    @Test
    public void should_get_team_by_id_when_id_exists()
    {
        when(repo.getTeamById(1))
                .thenReturn(team);

        TeamDN result = usecase.execute(1);

        assertEquals(team, result);
        List<PersonDN> resultMembers = result.getPersons();
        List<PersonDN> expectedMembers = team.getPersons();
        assertEquals(expectedMembers.size(), resultMembers.size());
        for (int i = 0; i < expectedMembers.size(); i++) {
            UtilsTest.testPerson(expectedMembers.get(i), resultMembers.get(i));
        }
        verify(repo).getTeamById(1);
    }

    @Test
    public void should_return_null_when_id_does_not_exist() {
        // Simulate the repository returning null when the ID does not exist
        when(repo.getTeamById(999)).thenReturn(null);

        TeamDN result = usecase.execute(999);

        assertNull(result);

        verify(repo).getTeamById(999);
    }

    @Test
    public void should_throw_exception_when_id_is_invalid() {

        assertThrows(InvalidParamsExeception.class, () -> {
            usecase.execute(0);
        });
        assertThrows(InvalidParamsExeception.class, () -> {
            usecase.execute(-1);
        });
    }
}
