package usecases.person;

import lni.archi.hexa.core.exceptions.job.InvalidParamsExeception;
import lni.archi.hexa.core.exceptions.job.JobException;
import lni.archi.hexa.core.usescases.person.CreatePersonUE;
import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.data.jpa.repositories.PersonJpaRepo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import usecases.UtilsTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreatePersonUETest_UT {

    @Mock
    private PersonJpaRepo repo;

    private CreatePersonUE usecase;

    private PersonDN person;

    @Before
    public void setUp()
    {
        MockitoAnnotations.openMocks(this);
        this.usecase = new CreatePersonUE(this.repo);
    }

    @Test
    public void should_create_person_when_all_params_are_valid()
    {
        PersonDN expectedPerson = new PersonDN(1, "lorentin", "nicolas", 22);

        when(repo.createPerson("lorentin", "nicolas", 22))
                .thenReturn(expectedPerson);

        PersonDN result = usecase.execute("lorentin", "nicolas", 22);

        assertEquals(expectedPerson, result);
        UtilsTest.testPerson(expectedPerson, result);

        verify(repo).createPerson("lorentin", "nicolas", 22);
    }

    @Test
    public void should_throw_exception_when_firstName_is_null() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute(null, "nicolas", 22)
        );
        assertEquals("Firstname must be between 2 and 30 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_firstName_is_too_short() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute("l", "nicolas", 22)
        );
        assertEquals("Firstname must be between 2 and 30 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_firstName_is_too_long() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute("lorentinnicolaslorentinnicolasZ", "nicolas", 22)
        );
        assertEquals("Firstname must be between 2 and 30 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_name_is_null() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute("lorentin", null, 22)
        );
        assertEquals("Name must be between 1 and 40 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_name_is_too_short() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute("lorentin", "n", 22)
        );
        assertEquals("Name must be between 1 and 40 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_name_is_too_long() {
        JobException exception = assertThrows(InvalidParamsExeception.class,
                () -> this.usecase.execute("lorentin", "nicolasnicolasnicolasnicolasnicolasZzzzzz", 22)
        );
        assertEquals("Name must be between 1 and 40 characters and not null", exception.getMessage());
    }

    @Test
    public void should_throw_exception_when_age_is_negative() {
        JobException exception = assertThrows(InvalidParamsExeception.class, () -> {
            this.usecase.execute("lorentin", "nicolas", 0);
        });
        assertEquals("Age must be greater than 0", exception.getMessage());
        exception = assertThrows(InvalidParamsExeception.class, () -> {
            this.usecase.execute("lorentin", "nicolas", -1);
        });
        assertEquals("Age must be greater than 0", exception.getMessage());
    }

}
