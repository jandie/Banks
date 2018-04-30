package rabo.logic;

import rabo.domain.RaboFeedback;
import rabo.domain.RaboTransaction;
import rabo.domain.enums.RaboState;
import rabo.messaging.feedback.RaboFeedbackReceiver;
import rabo.messaging.feedback.RaboFeedbackSender;
import rabo.messaging.transaction.RaboTransactionReceiver;
import rabo.messaging.transaction.RaboTransactionSender;

public class RaboMessageLogic {
    private Double transactionCounter = 0.0;

    public RaboMessageLogic() {
        new RaboTransactionReceiver() {
            @Override
            public void handleNewTransaction(RaboTransaction transaction) {
                RaboMessageLogic.this.handleTransaction(transaction);
            }
        };
        new RaboFeedbackReceiver() {
            @Override
            public void handleNewFeedback(RaboFeedback feedback) {
                RaboMessageLogic.this.handleFeedback(feedback);
            }
        };
    }

    public void sendTransaction(String from, String to, double amount) {
        RaboTransaction transaction = new RaboTransaction(
                to,
                from,
                amount,
                transactionCounter.toString());

        new RaboTransactionSender().sendTransaction(transaction);

        System.out.println("Sent " + transaction);

        transactionCounter ++;
    }

    private void handleTransaction(RaboTransaction transaction) {
        System.out.println("Received " + transaction);

        sendFeedback(
                new RaboFeedback(
                        transaction,
                        RaboState.CONFIRMED,
                        "Transaction processed by Rabobank"));
    }

    private void handleFeedback(RaboFeedback feedback) {
        RaboTransaction transaction = feedback.getTransaction();
        transaction.setState(feedback.getState());
        System.out.println("Updated " + transaction + ", message: " + feedback.getMessage());
    }

    private void sendFeedback(RaboFeedback raboFeedback) {
        new RaboFeedbackSender().sendFeedback(raboFeedback);
    }
}
