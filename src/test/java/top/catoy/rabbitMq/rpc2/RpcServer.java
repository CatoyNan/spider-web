package top.catoy.rabbitMq.rpc2;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static com.rabbitmq.client.AMQP.PROTOCOL.PORT;

/**
 * @ClassName RpcServer
 * @Description TODO
 * @Author admin
 * @Date 2020-01-06 17:45
 * @Version 1.0
 **/
public class RpcServer {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private Connection connection;
    private Channel channel;
    String replyQueue;


    public static void main(String[] args) throws IOException, TimeoutException {
        RpcServer rpcServer = new RpcServer();
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        rpcServer.connection = connectionFactory.newConnection();
        rpcServer.channel = rpcServer.connection.createChannel();
        rpcServer.replyQueue = rpcServer.channel.queueDeclare().getQueue();
        rpcServer.channel.basicConsume("rpc_queue",false,(consumerTag, delivery) -> {
            AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                    .Builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                    .build();

            String response = "";

            try {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("message:" + message);

            } catch (RuntimeException e) {
                System.out.println(" [.] " + e.toString());
            } finally {
                rpcServer.channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                rpcServer.channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            }
        },(c)->{});
    }
}
