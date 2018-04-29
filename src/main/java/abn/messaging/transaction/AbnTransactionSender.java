package abn.messaging.transaction;

import abn.domain.AbnTransaction;
import abn.messaging.AbnTranslator;
import inter.domain.InterTransaction;
import message.MessageSender;
import util.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnTransactionSender {
    private MessageSender messageSender;
    private GsonUtil gsonUtil = new GsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    public void sendTransaction(AbnTransaction abnTransaction) {
        try {
            messageSender = new MessageSender("IntercomReceive");
            messageSender.send(
                    gsonUtil.encode(
                            abnTranslator.transaction(abnTransaction)));

        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}