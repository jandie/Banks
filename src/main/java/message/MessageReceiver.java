package message;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MessageReceiver {
    private Connection connection;
    private Channel channel;
    private String queueName;

    public MessageReceiver(String queue, boolean basicQos) throws IOException, TimeoutException,
            NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        queueName = queue;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv("RABBIT_MQ_URI"));
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queueName, true, false,
                false, null);

        if (basicQos)
            channel.basicQos(10);
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
