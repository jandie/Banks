import abn.domain.AbnTransaction;
import abn.messaging.transaction.AbnTransactionSender;
import org.junit.Test;

public class AbnIT {
    @Test
    public void SendTenThousandTransactions() {
        sendTransactions(10000);
    }

    @Test
    public void SendOneTransactions() {
        sendTransactions(1);
    }

    private void sendTransactions(int amount) {
        AbnTransactionSender abnTransactionSender = new AbnTransactionSender();

        for (Integer i = 0; i < amount; i++) {
            abnTransactionSender.sendTransaction(
                    new AbnTransaction("NLABNA0123456789",
                            "NLABNA0123456789", 1 + i, i.toString())
            );
        }
    }
}
