package abn.logic;

import abn.domain.AbnFeedback;
import abn.domain.AbnTransaction;
import abn.domain.enums.AbnState;
import abn.messaging.feedback.AbnFeedbackReceiver;
import abn.messaging.feedback.AbnFeedbackSender;
import abn.messaging.transaction.AbnTransactionReceiver;
import abn.messaging.transaction.AbnTransactionSender;

import java.util.ArrayList;
import java.util.Arrays;

public class AbnMessageLogic {
    private Double transactionCounter = 0.0;

    public AbnMessageLogic() {
        new AbnTransactionReceiver() {
            @Override
            public void handleNewTransaction(AbnTransaction transaction) {
                AbnMessageLogic.this.handleTransaction(transaction);
            }
        };
        new AbnFeedbackReceiver() {
            @Override
            public void handleNewFeedback(AbnFeedback feedback) {
                AbnMessageLogic.this.handleFeedback(feedback);
            }
        };
    }

    public void sendTransaction(String from, String to, double amount) {
        AbnTransaction transaction = new AbnTransaction(
                to,
                from,
                amount,
                new ArrayList<>(
                        Arrays.asList(transactionCounter.toString())));

        new AbnTransactionSender().sendTransaction(transaction);

        System.out.println("Sent " + transaction);

        transactionCounter ++;
    }

    private void handleTransaction(AbnTransaction transaction) {
        System.out.println("Received " + transaction);

        sendFeedback(
                new AbnFeedback(
                        transaction,
                        AbnState.CONFIRMED,
                        "Transaction processed by ABN-AMRO"));
    }

    private void handleFeedback(AbnFeedback feedback) {
        AbnTransaction transaction = feedback.getTransaction();
        transaction.setState(feedback.getState());
        System.out.println("Updated " + transaction + ", message: " + feedback.getMessage());
    }

    private void sendFeedback(AbnFeedback abnFeedback) {
        new AbnFeedbackSender().sendFeedback(abnFeedback);
    }
}
