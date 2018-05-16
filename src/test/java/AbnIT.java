import abn.domain.AbnTransaction;
import abn.messaging.transaction.AbnTransactionSender;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class AbnIT {
    @Test
    public void SendTenThousandTransactionsToRabo() {
        sendTransactionsToRabo(10000);
    }

    @Test
    public void SendOneTransactionsToRabo() {
        sendTransactionsToRabo(1);
    }

    private void sendTransactionsToRabo(int amount) {
        AbnTransactionSender abnTransactionSender = new AbnTransactionSender();

        for (Integer i = 0; i < amount; i++) {
            abnTransactionSender.sendTransaction(
                    new AbnTransaction("NLRABO0123456789",
                            "NLABNA0123456789",
                            1 + i, new ArrayList<>(
                            Arrays.asList(i.toString())))
            );
        }
    }
}
