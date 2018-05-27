package inter.repo;

import com.mongodb.*;
import inter.domain.InterTransaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionRepo {
    private MongoClient client;
    private DB db;

    public TransactionRepo() {
        String mongoUri = System.getenv("BANKS_MONGO_URI");
        MongoClientURI uri = new MongoClientURI(mongoUri);
        this.client = new MongoClient( uri );
        db = this.client.getDB("banks");
    }

    public void saveTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");
        collection.insert(TransactionAdaptor.toDbObject(transaction));

        System.out.println(TransactionAdaptor.toDbObject(transaction).toJson());
    }

    public void updateTransaction(InterTransaction transaction) {
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

    public void deleteSimilarTransaction(InterTransaction transaction) {
        DBCollection collection = db.getCollection("transaction");

        BasicDBObject searchQuery = new BasicDBObject()
                .append("fromAccount", transaction.getFromAccount())
                .append("toAccount", transaction.getToAccount());

        collection.remove(searchQuery);
    }

    public List<InterTransaction> getAllTransactions() {
        DBCollection collection = db.getCollection("transaction");
        DBCursor cursor = collection.find();
        List<InterTransaction> interTransactions = new ArrayList<>();

        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();

            InterTransaction trans =
                    TransactionAdaptor.toInterTransaction(obj);

            interTransactions.add(trans);
        }

        return interTransactions;
    }

    public List<InterTransaction> getAllAndDelete() {
        DBCollection collection = db.getCollection("transaction");
        DBCursor cursor = collection.find();
        List<InterTransaction> interTransactions = new ArrayList<>();

        while (cursor.hasNext()) {
            BasicDBObject obj = (BasicDBObject) cursor.next();

            collection.remove(obj);

            InterTransaction trans =
                    TransactionAdaptor.toInterTransaction(obj);

            interTransactions.add(trans);
        }

        return interTransactions;
    }

    public void deleteAll() {
        DBCollection collection = db.getCollection("transaction");
        // Delete All documents from collection using DBCursor
        DBCursor cursor = collection.find();
        while (cursor.hasNext()) {
            collection.remove(cursor.next());
        }
    }

    public void close() {
        client.close();
    }
}
