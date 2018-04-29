package abn.logic;

import abn.domain.AbnFeedback;
import abn.domain.AbnTransaction;
import abn.domain.enums.AbnState;
import abn.messaging.feedback.AbnFeedbackReceiver;
import abn.messaging.feedback.AbnFeedbackSender;
import abn.messaging.transaction.AbnTransactionReceiver;
import abn.messaging.transaction.AbnTransactionSender;

public class AbnMessageLogic {
    private Double transactionCounter = 0.0;
    private AbnTransactionSender abnTransactionSender;
    private AbnTransactionReceiver abnTransactionReceiver;

    private AbnFeedbackSender abnFeedbackSender;
    private AbnFeedbackReceiver abnFeedbackReceiver;

    public AbnMessageLogic() {
        abnTransactionSender = new AbnTransactionSender();
        abnFeedbackSender = new AbnFeedbackSender();

        abnTransactionReceiver = new AbnTransactionReceiver(){
            @Override
            public void handleNewTransaction(AbnTransaction transaction) {
                AbnMessageLogic.this.handleTransaction(transaction);
            }
        };
        abnFeedbackReceiver = new AbnFeedbackReceiver(){
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
                transactionCounter.toString());

        abnTransactionSender.sendTransaction(transaction);

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

        abnTransactionReceiver.acknowledge();
    }

    private void handleFeedback(AbnFeedback feedback) {
        AbnTransaction transaction = feedback.getTransaction();
        transaction.setState(feedback.getState());
        System.out.println("Updated " + transaction + ", message: " + feedback.getMessage());

        abnFeedbackReceiver.acknowledge();
    }

    private void sendFeedback(AbnFeedback abnFeedback) {
        abnFeedbackSender.sendFeedback(abnFeedback);
    }
}
