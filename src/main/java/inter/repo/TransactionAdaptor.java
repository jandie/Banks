package inter.repo;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import inter.domain.InterTransaction;

import java.util.ArrayList;
import java.util.List;

class TransactionAdaptor {
    static BasicDBObject toDbObject(InterTransaction transaction) {
        return new BasicDBObject()
                .append("toAccount", transaction.getToAccount())
                .append("fromAccount", transaction.getFromAccount())
                .append("toBankCode", transaction.getToBankCode())
                .append("fromBankCode", transaction.getFromBankCode())
                .append("fromReferences", transaction.getFromReferences())
                .append("amount", transaction.getAmount())
                .append("count", transaction.getCount());
    }

    static InterTransaction toInterTransaction(BasicDBObject obj) {
        BasicDBList refList = (BasicDBList) obj.get("fromReferences");
        List<String> references = new ArrayList<>();

        for (Object reference: refList) {
            references.add((String) reference);
        }

        InterTransaction interTransaction = new InterTransaction(
                obj.getString("toAccount"),
                obj.getString("fromAccount"),
                obj.getDouble("amount"),
                references
        );

        interTransaction.setCount(obj.getLong("count"));

        return interTransaction;
    }
}
