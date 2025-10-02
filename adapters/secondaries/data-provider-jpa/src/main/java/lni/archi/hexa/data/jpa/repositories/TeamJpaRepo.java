package lni.archi.hexa.data.jpa.repositories;

import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.techException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.tech.SqlException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class TeamJpaRepo implements ITeamRepoPT {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public TeamDN createTeam(String name, List<PersonDN> persons) {
        int generatedId;

        try {
            String queryStr = """
                    INSERT INTO team (name) values (:name)
                    """;
            Query query = entityManager.createNativeQuery(queryStr);
            query.setParameter("name", name);
            query.executeUpdate();

            String selectIdQuery = "SELECT teamId as generatedId FROM team ORDER BY teamId DESC LIMIT 1";
            Query selectQuery = entityManager.createNativeQuery(selectIdQuery);
            generatedId = ((Number) selectQuery.getSingleResult()).intValue();

            CreatelinkBetweenPersonAndTeam(generatedId, persons);
        } catch(SqlException e) {
            throw e;
        } catch(Exception e) {
            throw new SqlException("Error during INSERT team", TeamErrorMessage.CANNOT_CREATE_TEAM);
        }

        return new TeamDN(generatedId, name, persons);
    }

    private void CreatelinkBetweenPersonAndTeam(int teamId, List<PersonDN> persons) {
        try {
            String queryStr = """
                    INSERT INTO members (teamId, personID) VALUES (:teamId, :personId)
                    """;
            Query query = entityManager.createNativeQuery(queryStr);
            for (PersonDN person : persons) {
                query.setParameter("teamId", teamId);
                query.setParameter("personId", person.getId());
                query.executeUpdate();
            }
        } catch(Exception e) {
            throw new SqlException("Unable to link persons to team", TeamErrorMessage.CANNOT_LINK_PERSON_TO_TEAM);
        }
    }

    @Override
    public TeamDN getTeamById(Integer id) {
        TeamDN result = new TeamDN();
        String strQuery = """
                SELECT e.teamId          AS equipe_id,
                       e.name            AS equipe_nom,
                       p.personID        AS person_id,
                       p.firstName       AS person_nom,
                       p.name            AS person_prenom,
                       p.age             AS person_age
                FROM team e
                         JOIN members ep       ON e.teamId = ep.teamId
                         JOIN person p         ON p.personID = ep.personID
                WHERE e.teamId = :EquipeId
                ORDER BY e.teamId, p.personID;
                """;

        try {

            if (id == null) {
                throw new SqlException("Team's id can't be null", TeamErrorMessage.CANNOT_GET_TEAM_BY_ID_NULL);
            }

            List<Object[]> resultQ = entityManager.createNativeQuery(strQuery)
                        .setParameter("EquipeId", id)
                        .getResultList();
            if (resultQ.isEmpty()) {
                throw new SqlException("No team found with id: " + id, TeamErrorMessage.CANNOT_GET_TEAM_BY_ID_NOT_FOUND);
            }

            Object[] firstRow = resultQ.get(0);
            Integer teamId = ((Number) firstRow[0]).intValue();
            String teamName = (String) firstRow[1];

            List<PersonDN> members = new ArrayList<>();
            for (Object[] row : resultQ) {
                Integer personId = ((Number) row[2]).intValue();
                String personName = (String) row[3];
                String personFirstName = (String) row[4];
                int personAge = ((Number) row[5]).intValue();

                members.add(new PersonDN(personId, personName, personFirstName, personAge));
            }

            result.setId(teamId);
            result.setName(teamName);
            result.setPersons(members);
        } catch (SqlException e) {
            throw e;
        } catch (Exception e) {
            throw new SqlException("No team found with id: " + id, TeamErrorMessage.CANNOT_GET_TEAM_BY_ID_NOT_FOUND);
        }

        return result;
    }

    @Override
    public List<TeamDN> getAllTeam() {
        List<TeamDN> result = new ArrayList<>();
        String strQuery = """
                SELECT e.teamId       AS equipe_id,
                       e.name         AS equipe_nom
                FROM team e
                ORDER BY e.teamId;
                """;
        try {
            List<Object[]> resultQ = entityManager.createNativeQuery(strQuery).getResultList();
            if (resultQ.isEmpty()) {
                throw new SqlException("No team found.", TeamErrorMessage.CANNOT_GET_ALL_TEAM);
            }

            for (Object[] row : resultQ) {
                Integer teamId = ((Number) row[0]).intValue();
                String teamName = (String) row[1];
                TeamDN team = new TeamDN(teamId, teamName);
                result.add(team);
            }
        } catch (Exception e) {
            throw new SqlException("Error during SELECT all team", TeamErrorMessage.CANNOT_GET_ALL_TEAM);
        }

        return result;
    }
}
