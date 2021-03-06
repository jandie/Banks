package rabo.messaging.transaction;

import abn.messaging.AbnTranslator;
import message.MessageSender;
import rabo.domain.RaboTransaction;
import rabo.messaging.RaboTranslator;
import util.JsonUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("Duplicates")
public class RaboTransactionSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();
    private RaboTranslator raboTranslator = new RaboTranslator();

    public void sendTransaction(RaboTransaction raboTransaction) {
        try {
            messageSender = new MessageSender("IntercomReceive");
            messageSender.send(
                    jsonUtil.encode(
                            raboTranslator.transaction(raboTransaction)));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}