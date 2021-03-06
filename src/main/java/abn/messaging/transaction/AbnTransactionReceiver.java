package abn.messaging.transaction;

import abn.domain.AbnTransaction;
import abn.messaging.AbnTranslator;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterTransaction;
import message.MessageReceiver;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnTransactionReceiver {
    private MessageReceiver receiver;
    private JsonUtil jsonUtil = new JsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    public AbnTransactionReceiver() {
        try {
            receiver = new MessageReceiver("NLABNA", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");
                    InterTransaction abnTransaction = jsonUtil.decode(
                            json,
                            InterTransaction.class);

                    handleNewTransaction(abnTranslator.transaction(abnTransaction));
                    receiver.acknowledge(envelope);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewTransaction(AbnTransaction transaction) {
    }
}
