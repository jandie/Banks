package inter.messaging.transaction;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterTransaction;
import message.MessageReceiver;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TransactionReceiver {
    private MessageReceiver receiver;
    private JsonUtil jsonUtil = new JsonUtil();

    public TransactionReceiver() {
        try {
            receiver = new MessageReceiver("IntercomReceive", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");

                    InterTransaction transaction = jsonUtil.decode(
                            json,
                            InterTransaction.class);

                    handleNewTransaction(transaction);
                    receiver.acknowledge(envelope);
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void handleNewTransaction(InterTransaction transaction) {
    }
}
