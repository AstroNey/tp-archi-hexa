package lni.archi.hexa.data.jpa.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.RequestTypeE;
import lni.archi.hexa.core.exceptions.tech.SqlException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class PersonJpaRepo implements IPersonRepoPT {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PersonDN createPerson(String name, String firstname, int age){
        String strQuery;
        Object[] result;
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

            String selectIdQuery = "SELECT LAST_INSERT_ID() as generatedId";
            Query selectQuery = entityManager.createNativeQuery(selectIdQuery);
            generatedId = ((Number) selectQuery.getSingleResult()).intValue();
        } catch (Exception e) {
            throw new SqlException("Failed during SQL requests for createPerson", RequestTypeE.CREATE, e.getCause());
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
            throw new SqlException("Person id is null", RequestTypeE.FETCH);
        }
        try {
            Object[] resultQ = (Object[]) entityManager.createNativeQuery(strQuery.toString())
                    .setParameter("PersonId", id)
                    .getSingleResult();
            entityManager.clear();
            entityManager.close();

            result.setId((Integer) resultQ[0]);
            result.setName((String) resultQ[1]);
            result.setFirstName((String) resultQ[2]);
            result.setAge((Integer) resultQ[3]);

        } catch (Exception e) {
            throw new SqlException("No person found with id: " + id, RequestTypeE.FETCH, e.getCause());
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
            entityManager.clear();
            entityManager.close();

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
            throw new SqlException("Failed during SQL requests for getAllPerson", RequestTypeE.FECTH_ALL, e.getCause());
        }

        return result;
    }
}
