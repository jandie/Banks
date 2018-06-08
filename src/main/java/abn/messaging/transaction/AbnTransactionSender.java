package abn.messaging.transaction;

import abn.domain.AbnTransaction;
import abn.messaging.AbnTranslator;
import message.MessageSender;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnTransactionSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    @SuppressWarnings("Duplicates")
    public void sendTransaction(AbnTransaction abnTransaction) {
        try {
            messageSender = new MessageSender("IntercomReceive");
            messageSender.send(
                    jsonUtil.encode(
                            abnTranslator.transaction(abnTransaction)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}