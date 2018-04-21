package inter.messaging;

import inter.domain.InterTransaction;
import message.MessageSender;
import util.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionSender {
    private MessageSender messageSender;
    private GsonUtil gsonUtil = new GsonUtil();

    public void sendTransaction(InterTransaction transaction) {
        try {
            messageSender = new MessageSender(transaction.getToBankCode());
            messageSender.send(gsonUtil.encode(transaction));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}
