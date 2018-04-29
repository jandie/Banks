package inter.messaging.feedback;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterFeedback;
import inter.domain.InterTransaction;
import message.MessageReceiver;
import util.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FeedbackReceiver {
    private MessageReceiver receiver;
    private Envelope currentEnvelope;
    private GsonUtil gsonUtil = new GsonUtil();

    public FeedbackReceiver() {
        try {
            receiver = new MessageReceiver("InterFeedReceive", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");
                    InterFeedback feedback = gsonUtil.decode(
                            json,
                            InterFeedback.class);

                    handleNewFeedback(feedback);

                    System.out.println(" [x] Received '" + json + "'");
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void handleNewFeedback(InterFeedback feedback) {
    }

    public void acknowledge() {
        try {
            receiver.acknowledge(currentEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
