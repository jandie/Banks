package inter.logic;

import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import inter.domain.enums.InterState;
import inter.messaging.feedback.FeedbackReceiver;
import inter.messaging.feedback.FeedbackSender;
import inter.messaging.transaction.TransactionReceiver;
import inter.messaging.transaction.TransactionSender;

public class RouterLogic {
    private TransactionReceiver transactionReceiver;
    private TransactionSender transactionSender;

    private FeedbackReceiver feedbackReceiver;
    private FeedbackSender feedbackSender;

    public RouterLogic() {
        transactionSender = new TransactionSender();
        feedbackSender = new FeedbackSender();

        transactionReceiver = new TransactionReceiver(){
            @Override
            public void handleNewTransaction(InterTransaction transaction) {
                RouterLogic.this.handleNewTransaction(transaction);
            }
        };

        feedbackReceiver = new FeedbackReceiver(){
            @Override
            public void handleNewFeedback(InterFeedback feedback) {
                RouterLogic.this.handleNewFeedback(feedback);
            }
        };
    }

    private void handleNewTransaction(InterTransaction transaction) {
        transactionSender.sendTransaction(transaction);
        transactionReceiver.acknowledge();

        feedbackSender.sendFeedback(
                new InterFeedback(
                        transaction,
                        InterState.ROUTED,
                        "Interrouter routed this transaction"
                )
        );

        System.out.println("Routed " + transaction);
    }

    private void handleNewFeedback(InterFeedback feedback) {
        feedbackSender.sendFeedback(feedback);
        feedbackReceiver.acknowledge();

        System.out.println("Handled feedback");
    }
}
