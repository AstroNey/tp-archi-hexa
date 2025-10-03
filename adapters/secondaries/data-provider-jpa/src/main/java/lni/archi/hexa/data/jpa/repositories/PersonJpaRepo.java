package lni.archi.hexa.data.jpa.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage;
import lni.archi.hexa.core.exceptions.tech.SqlException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PersonJpaRepo implements IPersonRepoPT {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("JpaQueryApiInspection")
    @Override
    public PersonDN createPerson(String name, String firstname, int age){
        String strQuery;
        int generatedId;

        try {
            strQuery = """
                INSERT INTO person (name, firstName, age)
                VALUES (:Name, :FirstName, :Age)""";

            Query query = entityManager.createNativeQuery(strQuery);
            query.setParameter("Name", name)
                    .setParameter("FirstName", firstname)
                    .setParameter("Age", age)
                    .executeUpdate();

            String selectIdQuery = "SELECT personId as generatedId FROM person ORDER BY personId DESC LIMIT 1";
            Query selectQuery = entityManager.createNativeQuery(selectIdQuery);
            generatedId = ((Number) selectQuery.getSingleResult()).intValue();
        } catch (Exception e) {
            throw new SqlException("Error during INSERT person", PersonErrorMessage.CANNOT_CREATE_PERSON);
        }

        return new PersonDN(generatedId, name, firstname, age);
    }

    @Override
    public PersonDN getPersonById(Integer id) {
        PersonDN result = new PersonDN();
        StringBuilder strQuery = new StringBuilder("""
                SELECT personId, name, firstName, age
                FROM person
                WHERE personId = :PersonId""");

        if (id == null) {
            throw new SqlException("Person's id can't be null", PersonErrorMessage.CANNOT_GET_PERSON_BY_ID_NULL);
        }
        try {
            Object[] resultQ = (Object[]) entityManager.createNativeQuery(strQuery.toString())
                    .setParameter("PersonId", id)
                    .getSingleResult();

            result.setId((Integer) resultQ[0]);
            result.setName((String) resultQ[1]);
            result.setFirstName((String) resultQ[2]);
            result.setAge((Integer) resultQ[3]);

        } catch (Exception e) {
            throw new SqlException("No person found with id: " + id, PersonErrorMessage.CANNOT_GET_PERSON_BY_ID_NOT_FOUND);
        }

        return result;
    }

    @Override
    public List<PersonDN> getAllPerson(Integer pageLimit, Integer page) {
        List<PersonDN> result = new ArrayList<>();

        Integer offset = (page - 1) * pageLimit;

        StringBuilder strQuery = new StringBuilder("""
                SELECT personId, name, firstName, age
                FROM person
                LIMIT :PageLimit
                OFFSET :Offset""");

        try {
            List resultQ = entityManager.createNativeQuery(strQuery.toString())
                                        .setParameter("PageLimit", pageLimit)
                                        .setParameter("Offset", offset)
                                        .getResultList();

            resultQ.forEach(person -> {
                Object[] row = (Object[]) person;
                result.add(new PersonDN(
                        (Integer) row[0],
                        (String) row[1],
                        (String) row[2],
                        (Integer) row[3]
                ));
            });
        } catch (Exception e) {
            throw new SqlException("Error during SELECT all person", PersonErrorMessage.CANNOT_GET_ALL_PERSON);
        }

        return result;
    }

    @Override
    public List<PersonDN> getAllPerson() {
        List<PersonDN> result = new ArrayList<>();

        StringBuilder strQuery = new StringBuilder("""
                SELECT personId, name, firstName, age
                FROM person""");

        try {
            List resultQ = entityManager.createNativeQuery(strQuery.toString())
                    .getResultList();

            resultQ.forEach(person -> {
                Object[] row = (Object[]) person;
                result.add(new PersonDN(
                        (Integer) row[0],
                        (String) row[1],
                        (String) row[2],
                        (Integer) row[3]
                ));
            });
        } catch (Exception e) {
            throw new SqlException("Error during SELECT all person", PersonErrorMessage.CANNOT_GET_ALL_PERSON);
        }

        return result;
    }

    @Override
    public int getCountPersonUE() {
        int count;
        String strQuery = "SELECT COUNT(personId) FROM person";

        try {
            count = ((Number) entityManager.createNativeQuery(strQuery).getSingleResult()).intValue();
        } catch (Exception e) {
            throw new SqlException("Error during COUNT person", PersonErrorMessage.CANNOT_COUNT_PERSON);
        }

        return count;
    }
}
