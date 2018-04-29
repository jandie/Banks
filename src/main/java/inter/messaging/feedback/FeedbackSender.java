package inter.messaging.feedback;

import inter.domain.InterFeedback;
import message.MessageSender;
import util.JsonUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FeedbackSender {
    private MessageSender messageSender;
    private JsonUtil jsonUtil = new JsonUtil();

    public void sendFeedback(InterFeedback feedback) {
        try {
            messageSender = new MessageSender(
                    feedback.getTransaction().getFromBankCode() + "feedback");
            messageSender.send(jsonUtil.encode(feedback));
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            messageSender.close();
        }
    }
}
