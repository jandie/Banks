package abn.messaging.feedback;

import abn.domain.AbnFeedback;
import abn.messaging.AbnTranslator;
import inter.domain.InterFeedback;
import message.MessageSender;
import util.GsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AbnFeedbackSender {
    private MessageSender messageSender;
    private GsonUtil gsonUtil = new GsonUtil();
    private AbnTranslator abnTranslator = new AbnTranslator();

    public void sendFeedback(AbnFeedback abnFeedback) {
        try {
            messageSender = new MessageSender("InterFeedReceive");
            messageSender.send(
                    gsonUtil.encode(
                            abnTranslator.feedback(abnFeedback)));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}
