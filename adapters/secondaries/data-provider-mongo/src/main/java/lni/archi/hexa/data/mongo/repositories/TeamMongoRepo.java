package lni.archi.hexa.data.mongo.repositories;

import com.mongodb.client.*;
import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.domain.TeamDN;
import lni.archi.hexa.core.enums.exception.techException.PersonErrorMessage;
import lni.archi.hexa.core.enums.exception.techException.TeamErrorMessage;
import lni.archi.hexa.core.exceptions.tech.MongoDbException;
import lni.archi.hexa.core.ports.data.repositories.ITeamRepoPT;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class TeamMongoRepo implements ITeamRepoPT {
    @Override
    public TeamDN createTeam(String name, List<PersonDN> persons) {
        int generatedId;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("team");

            List<Integer> personIds = persons.stream().map(PersonDN::getId).toList();

            checkIfMembersExist(db, personIds);
            generatedId = MongoTool.GetNextSequence(db, "teamId");
            Document query = new Document("teamId", generatedId)
                    .append("name", name)
                    .append("members", personIds);
            collection.insertOne(query);
        } catch (Exception e) {
            throw new MongoDbException("Error during Create team DOCUMENT", TeamErrorMessage.CANNOT_CREATE_TEAM);
        }

        return new TeamDN(generatedId, name, persons);
    }

    @Override
    public TeamDN getTeamById(Integer id) {
        TeamDN result = new TeamDN();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("team");

            Document query = new Document("teamId", id);
            Document teamDoc = collection.find(query).first();

            if (teamDoc == null) {
                throw new MongoDbException("Team not Found", TeamErrorMessage.CANNOT_GET_TEAM_BY_ID_NULL);
            }

            result.setId(teamDoc.getInteger("teamId"));
            result.setName(teamDoc.getString("name"));
            result.setPersons(getPersonsByIds(teamDoc.getList("members", Integer.class)));
        } catch (Exception e) {
            throw new MongoDbException("Error during fetch Team by Id", TeamErrorMessage.CANNOT_GET_TEAM_BY_ID_NOT_FOUND);
        }

        return result;
    }

    private void checkIfMembersExist(MongoDatabase db, List<Integer> personIds) {
        try {
            MongoCollection<Document> collection = db.getCollection("person");

            Document query = new Document("personId", new Document("$in", personIds));
            long count = collection.countDocuments(query);

            if (count != personIds.size()) {
                throw new MongoDbException("One or more persons not found in this team", PersonErrorMessage.CANNOT_GET_ALL_PERSON_FOR_TEAM);
            }
        } catch (Exception e) {
            throw new MongoDbException("Error during checkIfMembersExist()", PersonErrorMessage.CANNOT_GET_ALL_PERSON_FOR_TEAM);
        }
    }

    private List<PersonDN> getPersonsByIds(List<Integer> personIds) {
        List<PersonDN> result = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("person");

            Document query = new Document("personId", new Document("$in", personIds));
            FindIterable<Document> persons = collection.find(query);

            for (Document personDoc : persons) {

                result.add(PersonMongoRepo.CreatePersonDN(personDoc));
            }
        } catch (Exception e) {
            throw e; //TODO manage exception
        }

        return result;
    }

    @Override
    public List<TeamDN> getAllTeam() {
        List<TeamDN> result = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("team");

            FindIterable<Document> teamDoc = collection.find();

            for (Document team : teamDoc) {
                TeamDN teamDN = new TeamDN();
                teamDN.setId(team.getInteger("teamId"));
                teamDN.setName(team.getString("name"));
                teamDN.setPersons(getPersonsByIds(team.getList("members", Integer.class)));
                result.add(teamDN);
            }
        } catch (Exception e) {
            throw new MongoDbException("Error during fetch All Teams", TeamErrorMessage.CANNOT_GET_ALL_TEAM);
        }

        return result;
    }
}
