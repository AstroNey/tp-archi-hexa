package lni.archi.hexa.data.jpa.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.exceptions.tech.SqlException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(GlobalSetupExtension.class)
public class TeamJpaRepoTest {

    private TeamJpaRepo repo;

    List<PersonDN> persons = List.of(
            new PersonDN(1, "John", "Smith", 25),
            new PersonDN(2, "Jane", "Doe", 30),
            new PersonDN(3, "Charlie", "Brown", 22),
            new PersonDN(4, "Emily", "Johnson", 28),
            new PersonDN(5, "Michael", "Williams", 35),
            new PersonDN(6, "Olivia", "Taylor", 27)
    );

    @BeforeEach
    public void setUp() {
        repo = new TeamJpaRepo();
        SharedTestDatabase.injectEntityManager(repo);
        SharedTestDatabase.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        SharedTestDatabase.rollbackTransaction();
    }

    @Test
    void testCreateTeamInvalidName() {
        assertThrows(SqlException.class, () -> repo.createTeam(null, persons), "Team name is null");
        assertThrows(SqlException.class, () -> repo.createTeam("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", persons), "Team name is too long");
    }

    @Test
    void testCreateTeamInvalidPersons() {
        assertThrows(SqlException.class, () -> repo.createTeam("Dream Team", null), "Persons list is null");
    }

    @Test
    void testCreateTeamValid() {
        TeamDN expected = new TeamDN(1, "Dream Team", persons);
        TeamDN createdTeam = repo.createTeam(expected.getName(), expected.getPersons());
        Assertions.assertNotNull(createdTeam.getId());
        Assertions.assertEquals(expected.getName(), createdTeam.getName());
        Assertions.assertEquals(expected.getPersons().size(), createdTeam.getPersons().size());

        for (int i = 0; i < createdTeam.getPersons().size(); i++) {
            PersonDN actual = createdTeam.getPersons().get(i);
            PersonDN expectedPerson = expected.getPersons().stream()
                                                           .filter(p -> p.getId().equals(actual.getId()))
                                                           .findFirst()
                                                           .orElse(null);
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expectedPerson.getName(), actual.getName()),
                    () -> Assertions.assertEquals(expectedPerson.getFirstName(), actual.getFirstName()),
                    () -> Assertions.assertEquals(expectedPerson.getAge(), actual.getAge())
            );
        }
    }

    @Test
    void testGetTeamByIdNotFound() {
        assertThrows(SqlException.class, () -> repo.getTeamById(null));
        assertThrows(SqlException.class, () -> repo.getTeamById(-1));
    }

    @Test
    void testGetTeamById() {
        TeamDN expected = new TeamDN(1, "Dream Team", persons);
        TeamDN result = repo.getTeamById(1);
        Assertions.assertEquals(expected, result);
        Assertions.assertEquals(expected.getName(), result.getName());

        Assertions.assertEquals(expected.getPersons().size(), result.getPersons().size());

        for (int i = 0; i < result.getPersons().size(); i++) {
            PersonDN actual = result.getPersons().get(i);
            PersonDN expectedPerson = expected.getPersons().stream()
                                                           .filter(p -> p.getId().equals(actual.getId()))
                                                           .findFirst()
                                                           .orElse(null);
            Assertions.assertAll(
                    () -> Assertions.assertEquals(expectedPerson.getName(), actual.getName()),
                    () -> Assertions.assertEquals(expectedPerson.getFirstName(), actual.getFirstName()),
                    () -> Assertions.assertEquals(expectedPerson.getAge(), actual.getAge())
            );
        }
    }

    @Test
    void testGetAllTeamsEmpty() {
        SharedTestDatabase.getEntityManager().createNativeQuery("DELETE FROM team").executeUpdate();
        assertThrows(SqlException.class, () -> repo.getTeamById(1), "No team found");
    }

    @Test
    void testGetAllTeams() {
        TeamDN team = new TeamDN(1, "Dream Team", persons);

        List<TeamDN> teams = repo.getAllTeam();

        Assertions.assertEquals(1, teams.size());
        Assertions.assertEquals(team, teams.get(0));
        Assertions.assertEquals(team.getName(), teams.get(0).getName());
    }
}
