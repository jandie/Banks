package rabo.messaging.feedback;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterFeedback;
import message.MessageReceiver;
import rabo.domain.RaboFeedback;
import rabo.messaging.RaboTranslator;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RaboFeedbackReceiver {
    private MessageReceiver receiver;
    private JsonUtil jsonUtil = new JsonUtil();
    private RaboTranslator raboTranslator = new RaboTranslator();

    public RaboFeedbackReceiver() {
        try {
            receiver = new MessageReceiver("NLRABOfeedback", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");
                    InterFeedback interFeedback = jsonUtil.decode(
                            json,
                            InterFeedback.class);

                    handleNewFeedback(raboTranslator.feedback(interFeedback));
                    receiver.acknowledge(envelope);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void handleNewFeedback(RaboFeedback feedback) {
    }
}
