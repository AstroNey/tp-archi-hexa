package lni.archi.hexa.data.mongo.repositories;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;

public class MongoTool {

    public static int GetNextSequence(MongoDatabase db, String toIncrement) {
        MongoCollection<Document> counters = db.getCollection("counters");

        Document updated = counters.findOneAndUpdate(
                Filters.eq("_id", toIncrement),
                Updates.inc("sequence_value", 1),
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER)
        );
        if (updated == null) {
            // If no document was found, create one with seq starting at 0
            Document newCounter = new Document("_id", toIncrement).append("sequence_value", 1);
            counters.insertOne(newCounter);
            return 0;
        }
        return updated.getInteger("sequence_value");
    }
}
