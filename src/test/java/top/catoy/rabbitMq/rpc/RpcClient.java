package top.catoy.rabbitMq.rpc;


import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Client
 * @Description TODO
 * @Author admin
 * @Date 2020-01-05 19:59
 * @Version 1.0
 **/
public class RpcClient {
    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String replyQueueName;

    public RpcClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        replyQueueName = channel.queueDeclare().getQueue();

    }
    public String call(String message) throws IOException {
        String corrId  = UUID.randomUUID().toString();

        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder()
                .correlationId(corrId)
                .replyTo(replyQueueName)
                .build();

        channel.basicPublish("",requestQueueName,properties,message.getBytes());

        final String[] response = new String[1];
        String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
            if (delivery.getProperties().getCorrelationId().equals(corrId)) {
                String s = new String(delivery.getBody(), "UTF-8");
                System.out.println(s+"wwww");
                response[0] = s;
            }
        }, consumerTag -> {
        });

        String s = response[0];
        System.out.println(s + "ggg");
        channel.basicCancel(ctag);
        return response[0];
    }


    public static void main(String[] args) throws IOException, TimeoutException {
        RpcClient rpcClient = new RpcClient();
        System.out.println(rpcClient.call("1"));
    }

}
