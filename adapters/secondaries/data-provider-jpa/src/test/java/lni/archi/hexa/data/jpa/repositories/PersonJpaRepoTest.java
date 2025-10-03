package lni.archi.hexa.data.jpa.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.exceptions.tech.SqlException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(GlobalSetupExtension.class)
class PersonJpaRepoTest {

    private PersonJpaRepo repo;

    @BeforeEach
    public void setUp() {
        repo = new PersonJpaRepo();
        SharedTestDatabase.injectEntityManager(repo);
        SharedTestDatabase.beginTransaction();
    }

    @AfterEach
    public void tearDown() {
        SharedTestDatabase.rollbackTransaction();
    }

    @Test
    void testCreatePersonInvalidName() {
        PersonDN personWithId = new PersonDN(1, "John", "Smith", 25);
        SqlException exception = assertThrows(SqlException.class,
                () -> repo.createPerson(null, personWithId.getFirstName(), personWithId.getAge()));
        Assertions.assertEquals("Error during INSERT person", exception.getMessage());
        exception = assertThrows(SqlException.class,
                () -> repo.createPerson("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", personWithId.getFirstName(), personWithId.getAge()));
        Assertions.assertEquals("Error during INSERT person", exception.getMessage());
    }

    @Test
    void  testCreatePersonInvalidAge() {
        PersonDN personWithId = new PersonDN(1, "John", "Smith", 25);
        SqlException exception = assertThrows(SqlException.class,
                () -> repo.createPerson(personWithId.getName(), personWithId.getFirstName(), -5));
        Assertions.assertEquals("Error during INSERT person", exception.getMessage());
    }

    @Test
    void testCreatePersonInvalidFirstName() {
        PersonDN personWithId = new PersonDN(1, "John", "Smith", 25);
        SqlException exception = assertThrows(SqlException.class,
                () -> repo.createPerson(personWithId.getName(), null, personWithId.getAge()));
        Assertions.assertEquals("Error during INSERT person", exception.getMessage());
        exception = assertThrows(SqlException.class,
                () -> repo.createPerson(personWithId.getName(), "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb", personWithId.getAge()));
        Assertions.assertEquals("Error during INSERT person", exception.getMessage());
    }

    @Test
    void testCreatePerson() {
        PersonDN expected = new PersonDN(1, "Johnson", "Emily", 28);
        PersonDN createdPerson = repo.createPerson(expected.getName(), expected.getFirstName(), expected.getAge());
        Assertions.assertNotNull(createdPerson.getId());
        Assertions.assertEquals(expected.getName(), createdPerson.getName());
        Assertions.assertEquals(expected.getFirstName(), createdPerson.getFirstName());
        Assertions.assertEquals(expected.getAge(), createdPerson.getAge());
    }

    @Test
    void testGetPersonByIdNotFound() {
        SqlException exception = assertThrows(SqlException.class, () -> repo.getPersonById(null));
        Assertions.assertEquals("Person's id can't be null", exception.getMessage());
        exception = assertThrows(SqlException.class, () -> repo.getPersonById(999));
        Assertions.assertEquals("No person found with id: 999", exception.getMessage());
    }

    @Test
    void testGetPersonById() {
        // Exemple take from insert
        PersonDN expected = new PersonDN(1, "Smith", "John", 25);
        PersonDN fetchedPerson = repo.getPersonById(expected.getId());
        Assertions.assertEquals(expected.getId(), fetchedPerson.getId());
        Assertions.assertEquals(expected.getName(), fetchedPerson.getName());
        Assertions.assertEquals(expected.getFirstName(), fetchedPerson.getFirstName());
        Assertions.assertEquals(expected.getAge(), fetchedPerson.getAge());
    }

    @Test
    void testGetAllPersonsEmpty() {
        // Clear the table first
        SharedTestDatabase.getEntityManager().createNativeQuery("DELETE FROM person").executeUpdate();
        List<PersonDN> persons = repo.getAllPerson(10,1);
        Assertions.assertTrue(persons.isEmpty());
    }

    @Test
    void testGetAllPersonsInSecondPage() {
        // Exemple take from insert
        PersonDN person1 = new PersonDN(4, "Johnson", "Emily", 28);
        PersonDN person2 = new PersonDN(5, "Williams", "Michael", 35);
        PersonDN person3 = new PersonDN(6, "Taylor", "Olivia", 27);

        List<PersonDN> persons = repo.getAllPerson(3,2);
        Assertions.assertEquals(3, persons.size());

        Assertions.assertEquals(person1.getId(), persons.get(0).getId());
        Assertions.assertEquals(person1.getName(), persons.get(0).getName());
        Assertions.assertEquals(person1.getFirstName(), persons.get(0).getFirstName());
        Assertions.assertEquals(person1.getAge(), persons.get(0).getAge());

        Assertions.assertEquals(person2.getId(), persons.get(1).getId());
        Assertions.assertEquals(person2.getName(), persons.get(1).getName());
        Assertions.assertEquals(person2.getFirstName(), persons.get(1).getFirstName());
        Assertions.assertEquals(person2.getAge(), persons.get(1).getAge());

        Assertions.assertEquals(person3.getId(), persons.get(2).getId());
        Assertions.assertEquals(person3.getName(), persons.get(2).getName());
        Assertions.assertEquals(person3.getFirstName(), persons.get(2).getFirstName());
        Assertions.assertEquals(person3.getAge(), persons.get(2).getAge());
    }

    @Test
    void testGetAllPersons() {
        // Exemple take from insert
        PersonDN person1 = new PersonDN(1, "Smith", "John", 25);
        PersonDN person2 = new PersonDN(2, "Doe", "Jane", 30);
        PersonDN person3 = new PersonDN(3, "Brown", "Charlie", 22);

        List<PersonDN> persons = repo.getAllPerson(6,1);
        Assertions.assertEquals(6, persons.size());

        Assertions.assertEquals(person1.getId(), persons.get(0).getId());
        Assertions.assertEquals(person1.getName(), persons.get(0).getName());
        Assertions.assertEquals(person1.getFirstName(), persons.get(0).getFirstName());
        Assertions.assertEquals(person1.getAge(), persons.get(0).getAge());

        Assertions.assertEquals(person2.getId(), persons.get(1).getId());
        Assertions.assertEquals(person2.getName(), persons.get(1).getName());
        Assertions.assertEquals(person2.getFirstName(), persons.get(1).getFirstName());
        Assertions.assertEquals(person2.getAge(), persons.get(1).getAge());

        Assertions.assertEquals(person3.getId(), persons.get(2).getId());
        Assertions.assertEquals(person3.getName(), persons.get(2).getName());
        Assertions.assertEquals(person3.getFirstName(), persons.get(2).getFirstName());
        Assertions.assertEquals(person3.getAge(), persons.get(2).getAge());
    }
}
