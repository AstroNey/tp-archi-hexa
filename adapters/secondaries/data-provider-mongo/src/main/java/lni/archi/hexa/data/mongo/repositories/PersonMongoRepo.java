package lni.archi.hexa.data.mongo.repositories;

import com.mongodb.client.*;
import lni.archi.hexa.core.domain.PersonDN;
import lni.archi.hexa.core.enums.exception.RequestTypeE;
import lni.archi.hexa.core.exceptions.tech.MongoDbException;
import lni.archi.hexa.core.ports.data.repositories.IPersonRepoPT;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class PersonMongoRepo implements IPersonRepoPT {

    @Override
    public PersonDN createPerson(String name, String firstname, int age) {
        int generatedId;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("person");

            generatedId = MongoTool.GetNextSequence(db, "personId");
            Document query = new Document("personId", generatedId)
                    .append("name", name)
                    .append("firstName", firstname)
                    .append("age", age);
            collection.insertOne(query);
        } catch (Exception e) {
            throw new MongoDbException("Error during create Person MongoDB", RequestTypeE.CREATE, e.getCause());
        }

        return new PersonDN(generatedId, firstname, name, age);
    }

    @Override
    public PersonDN getPersonById(Integer id) {
        PersonDN person;

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("person");

            Document query = new Document("personId", id);
            Document personDoc = collection.find(query).first();

            if (personDoc == null) {
                throw new MongoDbException("Person not found", RequestTypeE.FETCH);
            }
            person = PersonMongoRepo.CreatePersonDN(personDoc);
        } catch (Exception e) {
            throw new MongoDbException("Error during fecth Person MongoDB", RequestTypeE.FETCH, e.getCause());
        }

        return person;
    }

    @Override
    public List<PersonDN> getAllPerson() {
        List<PersonDN> result = new ArrayList<>();

        try (MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017")) {
            MongoDatabase db = mongoClient.getDatabase("tp_hexa");
            MongoCollection<Document> collection = db.getCollection("person");

            FindIterable<Document> persons = collection.find();

            for( Document personDoc : persons ) {
                result.add(PersonMongoRepo.CreatePersonDN(personDoc));
            }
        } catch (Exception e) {
            throw new MongoDbException("Error during request to MongoDB", RequestTypeE.FECTH_ALL,e.getCause());
        }

        return result;
    }

    static PersonDN CreatePersonDN(Document personDoc) {
        PersonDN person = new PersonDN();
        person.setId(personDoc.getInteger("personId"));
        person.setFirstName(personDoc.getString("firstName"));
        person.setName(personDoc.getString("name"));
        person.setAge(personDoc.getInteger("age"));
        return person;
    }
}
