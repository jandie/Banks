package inter.messaging.feedback;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterFeedback;
import message.MessageReceiver;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FeedbackReceiver {
    private MessageReceiver receiver;
    private JsonUtil jsonUtil = new JsonUtil();

    public FeedbackReceiver() {
        try {
            receiver = new MessageReceiver("InterFeedReceive", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");

                    InterFeedback feedback = jsonUtil.decode(
                            json,
                            InterFeedback.class);

                    handleNewFeedback(feedback);
                    receiver.acknowledge(envelope);
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void handleNewFeedback(InterFeedback feedback) {
    }
}
