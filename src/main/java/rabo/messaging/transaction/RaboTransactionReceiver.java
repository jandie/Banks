package rabo.messaging.transaction;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterTransaction;
import message.MessageReceiver;
import rabo.domain.RaboTransaction;
import rabo.messaging.RaboTranslator;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RaboTransactionReceiver {
    private MessageReceiver receiver;
    private JsonUtil jsonUtil = new JsonUtil();
    private RaboTranslator raboTranslator = new RaboTranslator();

    public RaboTransactionReceiver() {
        try {
            receiver = new MessageReceiver("NLRABO", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");
                    InterTransaction abnTransaction = jsonUtil.decode(
                            json,
                            InterTransaction.class);

                    handleNewTransaction(raboTranslator.transaction(abnTransaction));
                    receiver.acknowledge(envelope);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewTransaction(RaboTransaction transaction) {
    }
}
