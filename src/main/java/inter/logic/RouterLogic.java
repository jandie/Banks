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

public class RouterLogic {

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

    private void handleNewTransaction(InterTransaction transaction) {
        TransactionRepo repo = new TransactionRepo();
        InterTransaction similarTransaction =
                repo.getSimilarTransaction(transaction);

        if (similarTransaction != null &&
                similarTransaction.getCount() > 10) {
            similarTransaction.increaseCount();
            similarTransaction.addAmount(transaction.getAmount());
            similarTransaction.addFromReferences(transaction.getFromReferences());
            repo.UpdateTransaction(similarTransaction);
        }
        else if(similarTransaction != null && similarTransaction.getCount() <= 10) {
            similarTransaction.increaseCount();
            repo.UpdateTransaction(similarTransaction);
            new TransactionSender().sendTransaction(transaction);
        }
        else {
            repo.SaveTransaction(new InterTransaction(
                    transaction.getToAccount(),
                    transaction.getFromAccount(),
                    0,
                    new ArrayList<>()));
            new TransactionSender().sendTransaction(transaction);
        }

        new FeedbackSender().sendFeedback(
                new InterFeedback(
                        transaction,
                        InterState.ROUTED,
                        "Interrouter routed this transaction"
                )
        );

        System.out.println("Routed " + transaction);
    }

    private void handleNewFeedback(InterFeedback feedback) {
        new FeedbackSender().sendFeedback(feedback);

        System.out.println("Handled feedback");
    }
}
