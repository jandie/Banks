package rabo.messaging.feedback;

import message.MessageSender;
import rabo.domain.RaboFeedback;
import rabo.messaging.RaboTranslator;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SuppressWarnings("Duplicates")
public class RaboFeedbackSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();
    private RaboTranslator raboTranslator = new RaboTranslator();

    public void sendFeedback(RaboFeedback raboFeedback) {
        try {
            messageSender = new MessageSender("InterFeedReceive");
            messageSender.send(
                    jsonUtil.encode(
                            raboTranslator.feedback(raboFeedback)));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}
