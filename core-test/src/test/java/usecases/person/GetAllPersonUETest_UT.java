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
    public void should_get_persons_if_page_is_within_available_data() {
        // Arrange
        Integer pageLimit = 2;
        Integer page = 1;

        PersonDN person = new PersonDN(3, "Alice", "Johnson", 28);
        PersonDN person2 = new PersonDN(4, "Bob", "Brown", 35);
        List<PersonDN> persons = List.of(person, person2);

        when(repo.getAllPerson(pageLimit, page)).thenReturn(persons);

        // Act
        List<PersonDN> result = useCase.execute(pageLimit, page);

        // Assert
        assertEquals(2, result.size());
        assertEquals(persons, result);
        UtilsTest.testPerson(person, result.get(0));
        UtilsTest.testPerson(person2, result.get(1));

        verify(repo).getAllPerson(pageLimit, page);
    }

    @Test
    public void should_get_remaining_persons_if_last_page_not_full() {
        // Arrange
        Integer pageLimit = 2;
        Integer page = 2; // OFFSET = 2

        PersonDN person3 = new PersonDN(5, "Charlie", "Davis", 40);
        List<PersonDN> persons = List.of(person3); // il ne reste qu'une personne

        when(repo.getAllPerson(pageLimit, page)).thenReturn(persons);

        // Act
        List<PersonDN> result = useCase.execute(pageLimit, page);

        // Assert
        assertEquals(1, result.size());
        assertEquals(person3, result.get(0));
        UtilsTest.testPerson(person3, result.get(0));

        verify(repo).getAllPerson(pageLimit, page);
    }
}
