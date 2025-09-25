package usecases.team;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.usescases.team.CreateTeamUE;
import lni.archi.hexa.data.jpa.repositories.TeamJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import usecases.UtilsTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateTeamUETest_UT {

    @Mock
    private TeamJpaRepo repo;

    private CreateTeamUE usecase;

    private final List<PersonDN> personsEmpty = List.of();

    private final List<PersonDN> personsValid = List.of(
            new PersonDN(1, "John", "Doe", 30),
            new PersonDN(2, "Jane", "Smith", 25),
            new PersonDN(3, "Alice", "Johnson", 28),
            new PersonDN(4, "Bob", "Brown", 35),
            new PersonDN(5, "Charlie", "Davis", 22),
            new PersonDN(6, "Eve", "Wilson", 27),
            new PersonDN(7, "Frank", "Miller", 40)
    );

    private final List<PersonDN> personsInvalid = List.of(
            new PersonDN(1, "test", "Doe", 30),
            new PersonDN(2, "Jane", "Johnson", 25),
            new PersonDN(3, "Alice", "Johnson", 8),
            new PersonDN(4, "Bob", "Brown", 17),
            new PersonDN(5, "Charlie", "Davis", 22),
            new PersonDN(6, "Eve", "Wilson", 27),
            new PersonDN(7, "Frank", "Miller", 15)
    );

    private final List<PersonDN> tooLong = List.of(
            new PersonDN(1, "John", "Doe", 30),
            new PersonDN(2, "Jane", "Smith", 25),
            new PersonDN(3, "Alice", "Johnson", 28),
            new PersonDN(4, "Bob", "Brown", 35),
            new PersonDN(5, "Charlie", "Davis", 22),
            new PersonDN(6, "Eve", "Wilson", 27),
            new PersonDN(7, "Frank", "Miller", 40),
            new PersonDN(8, "Grace", "Lee", 29),
            new PersonDN(9, "Hank", "Taylor", 33),
            new PersonDN(10, "Ivy", "Anderson", 26),
            new PersonDN(11, "Jack", "Thomas", 31),
            new PersonDN(12, "Kathy", "Moore", 24),
            new PersonDN(13, "Leo", "Martin", 38),
            new PersonDN(14, "Mia", "Jackson", 21),
            new PersonDN(15, "Nina", "White", 23),
            new PersonDN(16, "Oscar", "Harris", 34)
    );

    @Before
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        this.usecase = new CreateTeamUE(this.repo);
    }

    @Test
    public void should_create_team_when_all_params_are_valid()
    {
        TeamDN expectedTeam = new TeamDN(1, "teamA", personsValid);

        when(repo.createTeam("teamA", personsValid))
                .thenReturn(expectedTeam);

        TeamDN result = usecase.execute("teamA", personsValid);

        assertEquals(expectedTeam, result);
        assertEquals(expectedTeam.getPersons().size(), result.getPersons().size());
        assertEquals(expectedTeam.getPersons(), result.getPersons());

        List<PersonDN> resultMembers = result.getPersons();
        List<PersonDN> expectedMembers = expectedTeam.getPersons();
        assertEquals(expectedMembers.size(), resultMembers.size());
        for (int i = 0; i < expectedMembers.size(); i++) {
            UtilsTest.testPerson(expectedMembers.get(i), resultMembers.get(i));
        }

        verify(repo).createTeam("teamA", personsValid);
    }

    @Test
    public void should_throw_exception_when_name_is_null() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute(null, personsValid));
    }

    @Test
    public void should_throw_exception_when_name_is_too_short() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("tea", personsValid));
    }

    @Test
    public void should_throw_exception_when_name_is_too_long() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("teamAteamAteamAteamAteamA", personsValid));
    }

    @Test
    public void should_throw_exception_when_persons_list_is_null() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("teamA", null));
    }

    @Test
    public void should_throw_exception_when_persons_list_is_empty() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("teamA", personsEmpty));
    }

    @Test
    public void should_throw_exception_when_persons_list_is_too_long() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("teamA", tooLong));
    }

    @Test
    public void should_throw_exception_when_persons_list_contains_invalid_persons() {
        assertThrows(InvalidParamsExeception.class, () -> this.usecase.execute("teamA", personsInvalid));
    }
}

