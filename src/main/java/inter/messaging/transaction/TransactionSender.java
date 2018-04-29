package inter.messaging.transaction;

import inter.domain.InterTransaction;
import message.MessageSender;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();

    public void sendTransaction(InterTransaction transaction) {
        try {
            messageSender = new MessageSender(transaction.getToBankCode());
            messageSender.send(jsonUtil.encode(transaction));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}