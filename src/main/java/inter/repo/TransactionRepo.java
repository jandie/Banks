package inter.repo;

import com.mongodb.*;
import inter.domain.InterTransaction;

public class TransactionRepo {
    private DB db;

    public TransactionRepo() {
        db = new MongoClient( "localhost" , 27017 )
                .getDB("banks");
    }

    public void SaveTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");
        collection.insert(TransactionAdaptor.toDbObject(transaction));

        System.out.println(TransactionAdaptor.toDbObject(transaction).toJson());
    }

    public void UpdateTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");

        BasicDBObject searchQuery = new BasicDBObject()
                .append("fromAccount", transaction.getFromAccount())
                .append("toAccount", transaction.getToAccount());

        BasicDBObject newDocument = new BasicDBObject()
                .append("$set", TransactionAdaptor.toDbObject(transaction));

        collection.update(searchQuery, newDocument);
    }

    public InterTransaction getSimilarTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");

        BasicDBObject searchQuery = new BasicDBObject()
                .append("fromAccount", transaction.getFromAccount())
                .append("toAccount", transaction.getToAccount());

        DBCursor cursor = collection.find(searchQuery);

        if (cursor.hasNext()) {
            return TransactionAdaptor.toInterTransaction((BasicDBObject) cursor.next());
        }

        return null;
    }

    public void DeleteSimilarTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");

        BasicDBObject searchQuery = new BasicDBObject()
                .append("fromAccount", transaction.getFromAccount())
                .append("toAccount", transaction.getToAccount());

        collection.remove(searchQuery);
    }
}
