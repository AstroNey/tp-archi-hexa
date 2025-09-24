package usecases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.usescases.person.GetPersonByIdUE;
import lni.archi.hexa.data.jpa.repositories.PersonJpaRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import usecases.UtilsTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetPersonByIdUETest_UT {

    @Mock
    private PersonJpaRepo repo;

    private GetPersonByIdUE usecase;

    private PersonDN person;

    @Before
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        this.usecase = new GetPersonByIdUE(this.repo);
    }

    @Test
    public void should_get_person_by_id_when_id_exists()
    {
        PersonDN expectedPerson = new PersonDN(1, "lorentin", "nicolas", 22);

        when(repo.getPersonById(1))
                .thenReturn(expectedPerson);

        PersonDN result = usecase.execute(1);

        assertEquals(expectedPerson, result);
        UtilsTest.testPerson(expectedPerson, result);

        verify(repo).getPersonById(1);
    }

    @Test
    public void should_return_null_when_id_does_not_exist() {
        // Simulate the repository returning null when the ID does not exist
        when(repo.getPersonById(999)).thenReturn(null);

        PersonDN result = usecase.execute(999);

        assertNull(result);

        verify(repo).getPersonById(999);
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
