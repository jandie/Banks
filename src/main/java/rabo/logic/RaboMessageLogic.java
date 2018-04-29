package rabo.logic;

import abn.messaging.transaction.AbnTransactionSender;
import rabo.domain.RaboFeedback;
import rabo.domain.RaboTransaction;
import rabo.domain.enums.RaboState;
import rabo.messaging.feedback.RaboFeedbackReceiver;
import rabo.messaging.feedback.RaboFeedbackSender;
import rabo.messaging.transaction.RaboTransactionReceiver;
import rabo.messaging.transaction.RaboTransactionSender;

public class RaboMessageLogic {
    private Double transactionCounter = 0.0;
    private RaboTransactionSender raboTransactionSender;
    private RaboTransactionReceiver raboTransactionReceiver;

    private RaboFeedbackSender raboFeedbackSender;
    private RaboFeedbackReceiver raboFeedbackReceiver;

    public RaboMessageLogic() {
        raboTransactionSender = new RaboTransactionSender();
        raboFeedbackSender = new RaboFeedbackSender();

        raboTransactionReceiver = new RaboTransactionReceiver(){
            @Override
            public void handleNewTransaction(RaboTransaction transaction) {
                RaboMessageLogic.this.handleTransaction(transaction);
            }
        };
        raboFeedbackReceiver = new RaboFeedbackReceiver(){
            @Override
            public void handleNewFeedback(RaboFeedback feedback) {
                RaboMessageLogic.this.handleFeedback(feedback);
            }
        };
    }

    public synchronized void sendTransaction(String from, String to, double amount) {
        RaboTransaction transaction = new RaboTransaction(
                to,
                from,
                amount,
                transactionCounter.toString());

        raboTransactionSender.sendTransaction(transaction);

        System.out.println("Sent " + transaction);

        transactionCounter ++;
    }

    private synchronized void handleTransaction(RaboTransaction transaction) {
        System.out.println("Received " + transaction);

        sendFeedback(
                new RaboFeedback(
                        transaction,
                        RaboState.CONFIRMED,
                        "Transaction processed by Rabobank"));

        raboTransactionReceiver.acknowledge();
    }

    private synchronized void handleFeedback(RaboFeedback feedback) {
        RaboTransaction transaction = feedback.getTransaction();
        transaction.setState(feedback.getState());
        System.out.println("Updated " + transaction + ", message: " + feedback.getMessage());

        raboFeedbackReceiver.acknowledge();
    }

    private synchronized void sendFeedback(RaboFeedback raboFeedback) {
        raboFeedbackSender.sendFeedback(raboFeedback);
    }
}
