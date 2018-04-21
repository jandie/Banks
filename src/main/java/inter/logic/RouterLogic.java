package inter.logic;

import inter.domain.InterTransaction;
import inter.messaging.TransactionReceiver;
import inter.messaging.TransactionSender;

public class RouterLogic {
    private TransactionReceiver transactionReceiver;
    private TransactionSender transactionSender;

    public RouterLogic() {
        transactionSender = new TransactionSender();

        transactionReceiver = new TransactionReceiver(){
            @Override
            public void handleNewTransaction(InterTransaction transaction) {
                RouterLogic.this.handleNewTransaction(transaction);
            }
        };
    }

    private void handleNewTransaction(InterTransaction transaction) {
        transactionSender.sendTransaction(transaction);
    }
}
