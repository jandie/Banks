package inter.logic;

import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import inter.domain.enums.InterState;
import inter.messaging.feedback.FeedbackReceiver;
import inter.messaging.feedback.FeedbackSender;
import inter.messaging.transaction.TransactionReceiver;
import inter.messaging.transaction.TransactionSender;

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
        new TransactionSender().sendTransaction(transaction);

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
