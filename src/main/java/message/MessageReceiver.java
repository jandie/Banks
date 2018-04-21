package message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MessageReceiver {
    private Connection connection;
    private Channel channel;
    private String queueName;

    public MessageReceiver(String queue, boolean basicQos) throws IOException, TimeoutException {
        queueName = queue;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queue, true, false,
                false, null);

        if (basicQos)
            channel.basicQos(1);
    }

    public void setListener(Consumer consumer) throws IOException {
        channel.basicConsume(queueName, false, consumer);
    }

    public void acknowledge(Envelope envelope) throws IOException {
        channel.basicAck(envelope.getDeliveryTag(), false);
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public Channel getChannel() {
        return channel;
    }
}
