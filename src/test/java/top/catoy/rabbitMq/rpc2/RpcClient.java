package top.catoy.rabbitMq.rpc2;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName RpcClient
 * @Description TODO
 * @Author admin
 * @Date 2020-01-06 16:44
 * @Version 1.0
 **/
public class RpcClient {
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;
    private static final String RPC_QUEUE_NAME = "rpc_queue";
    private Connection connection;
    private Channel channel;
    String replyQueue;

    public RpcClient() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(IP_ADDRESS);
        connectionFactory.setPort(PORT);
        connection = connectionFactory.newConnection();
        channel = connection.createChannel();
        replyQueue = channel.queueDeclare().getQueue();
    }

    public void call() throws IOException, TimeoutException {
        channel.basicPublish("",RPC_QUEUE_NAME,new AMQP.BasicProperties().builder()
                .replyTo(replyQueue)
                .correlationId("1")
                .build(),"hello".getBytes());

        String s = channel.basicConsume(replyQueue, false, (consumerTag, delivery) -> {
            System.out.println("返回的消息");
        }, (consumerTag) -> {
        });
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        RpcClient rpcClient = new RpcClient();
        rpcClient.call();
    }
}
