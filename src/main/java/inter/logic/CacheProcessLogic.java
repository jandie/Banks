package inter.logic;

import inter.domain.InterTransaction;
import inter.repo.TransactionRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheProcessLogic {
    private RouterLogic routerLogic;

    public CacheProcessLogic(RouterLogic routerLogic) {
        this.routerLogic = routerLogic;
    }

    public void processCache() {
        TransactionRepo repo = new TransactionRepo();
        List<InterTransaction> transactions = repo.getAllAndDelete();

        HashMap<String, InterTransaction> transactionsMap = combineTransactions(transactions);

        for(Map.Entry<String, InterTransaction> entry : transactionsMap.entrySet()) {
            routerLogic.sendTransaction(entry.getValue());
        }
    }

    private HashMap<String, InterTransaction> combineTransactions(List<InterTransaction> interTransactions) {
        HashMap<String, InterTransaction> transactionsMap = new HashMap<>();

        for (InterTransaction trans : interTransactions) {
            String key = trans.getToAccount() + trans.getFromAccount();

            if (!transactionsMap.containsKey(key)) {
                transactionsMap.put(key, trans);
            }
            else {
                transactionsMap.get(key).addAmount(trans.getAmount());
                transactionsMap.get(key).addFromReferences(trans.getFromReferences());
            }
        }

        return transactionsMap;
    }
}
