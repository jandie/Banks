package abn.messaging.feedback;

import abn.domain.AbnFeedback;
import abn.messaging.AbnTranslator;
import message.MessageSender;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnFeedbackSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    public void sendFeedback(AbnFeedback abnFeedback) {
        try {
            messageSender = new MessageSender("InterFeedReceive");
            messageSender.send(
                    jsonUtil.encode(
                            abnTranslator.feedback(abnFeedback)));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}
