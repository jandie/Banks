package message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

public class MessageSender {
    private Connection connection;
    private Channel channel;
    private String queueName;

    public MessageSender(String queue) throws IOException, TimeoutException,
            NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        queueName = queue;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri(System.getenv("RABBIT_MQ_URI"));
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(queue, true, false,
                false, null);
    }

    public void send(String body) throws IOException {
        channel.basicPublish("", queueName,
                MessageProperties.PERSISTENT_TEXT_PLAIN,
                body.getBytes());
    }

    public void close() {
        try {
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
