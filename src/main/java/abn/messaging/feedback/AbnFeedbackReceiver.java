package abn.messaging.feedback;

import abn.domain.AbnFeedback;
import abn.messaging.AbnTranslator;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import inter.domain.InterFeedback;
import message.MessageReceiver;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnFeedbackReceiver {
    private MessageReceiver receiver;
    private Envelope currentEnvelope;
    private JsonUtil jsonUtil = new JsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    public AbnFeedbackReceiver() {
        try {
            receiver = new MessageReceiver("NLABNAfeedback", true);

            receiver.setListener(new DefaultConsumer(receiver.getChannel()) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String json = new String(body, "UTF-8");
                    InterFeedback interFeedback = jsonUtil.decode(
                            json,
                            InterFeedback.class);

                    currentEnvelope = envelope;

                    handleNewFeedback(
                            abnTranslator.feedback(interFeedback)
                    );

                    //System.out.println(" [x] Received '" + json + "'");
                }
            });
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }

    public void handleNewFeedback(AbnFeedback feedback) {
    }

    public void acknowledge() {
        try {
            receiver.acknowledge(currentEnvelope);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
