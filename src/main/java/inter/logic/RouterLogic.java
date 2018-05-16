package inter.logic;

import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import inter.domain.enums.InterState;
import inter.messaging.feedback.FeedbackReceiver;
import inter.messaging.feedback.FeedbackSender;
import inter.messaging.transaction.TransactionReceiver;
import inter.messaging.transaction.TransactionSender;
import inter.repo.TransactionRepo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RouterLogic {
    private Map<String, Long> counter = Collections.synchronizedMap(new HashMap<>());
    private TransactionRepo repo = new TransactionRepo();

    public RouterLogic() {
        new TransactionReceiver() {
            @Override
            public void handleNewTransaction(InterTransaction transaction) {
                RouterLogic.this.handleNewTransaction(transaction);
            }
        };

        new FeedbackReceiver() {
            @Override
            public void handleNewFeedback(InterFeedback feedback) {
                RouterLogic.this.handleNewFeedback(feedback);
            }
        };

        System.out.println("Router initialized!");
    }

    private synchronized void increaseCounter(InterTransaction transaction) {
        String key = transaction.getToAccount() + transaction.getFromAccount();

        if (!counter.containsKey(key)) {
            counter.put(key, 0L);
        }

        counter.put(key, counter.get(key) + 1);
    }

    private synchronized boolean hasBigCounter(InterTransaction transaction) {
        String key = transaction.getToAccount() + transaction.getFromAccount();

        if (!counter.containsKey(key)) return false;

        return counter.get(key) > 10;
    }

    private synchronized void handleNewTransaction(InterTransaction transaction) {
        increaseCounter(transaction);

        if (hasBigCounter(transaction)) {
            repo.saveTransaction(transaction);
            System.out.println("Saved " + transaction);
        }
        else {
            sendTransaction(transaction);
            System.out.println("Routed " + transaction);
        }

        new FeedbackSender().sendFeedback(
                new InterFeedback(
                        transaction,
                        InterState.ROUTED,
                        "Interrouter routed this transaction"
                )
        );
    }

    public void sendTransaction(InterTransaction transaction) {
        new TransactionSender().sendTransaction(transaction);
    }

    private void handleNewFeedback(InterFeedback feedback) {
        new FeedbackSender().sendFeedback(feedback);

        System.out.println("Handled feedback");
    }
}
