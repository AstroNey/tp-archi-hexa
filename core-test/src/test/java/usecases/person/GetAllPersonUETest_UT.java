package usecases.person;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.usescases.person.GetAllPersonUE;
import lni.archi.hexa.data.jpa.repositories.PersonJpaRepo;
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
public class GetAllPersonUETest_UT {

    @Mock
    private PersonJpaRepo repo;

    private GetAllPersonUE useCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.useCase = new GetAllPersonUE(this.repo);
    }

    @Test
    public void should_get_all_persons() {
        PersonDN person = new PersonDN(1, "John", "Doe", 30);
        PersonDN person2 = new PersonDN(2, "Jane", "Smith", 25);
        List<PersonDN> persons = List.of(person, person2);

        when(repo.getAllPerson()).thenReturn(persons);

        List<PersonDN> result = useCase.execute();
        assertEquals(persons, result);
        assertEquals(2, result.size());

        UtilsTest.testPerson(person, result.get(0));
        UtilsTest.testPerson(person2, result.get(1));

        verify(repo).getAllPerson();
    }
}
