package top.catoy.rabbitMq;

import com.rabbitmq.client.*;
import org.omg.IOP.CodecPackage.InvalidTypeForEncoding;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Productor
 * @Description TODO
 * @Author admin
 * @Date 2019-12-26 15:08
 * @Version 1.0
 **/
public class Productor {
    private static final String EXCHANGE_NAME = "";
    private static final String ROUTING_KEY = "rountingkey";
    private static final String QUEUE_NAME = "hello";
    private static final String IP_ADDRESS = "127.0.0.1";
    private static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(IP_ADDRESS);
        factory.setPort(PORT);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
//        channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
//        channel.queueDeclare(QUEUE_NAME,true,false,false,null);
//        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
        String message = "hello world";
        channel.basicPublish("","rpc_queue", MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
        channel.close();
        connection.close();
    }
//    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
////        new Thread(()->{
////            ConnectionFactory factory = new ConnectionFactory();
////            factory.setHost(IP_ADDRESS);
////            factory.setPort(PORT);
////            factory.setUsername("guest");
////            factory.setPassword("guest");
////            try {
////                Connection connection = factory.newConnection();
////                Channel channel = connection.createChannel();
////                channel.exchangeDeclare(EXCHANGE_NAME,"direct",true,false,null);
////                channel.queueDeclare(QUEUE_NAME,true,false,false,null);
////                channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,ROUTING_KEY);
////                String message = "hello world";
////                while (true){
////                System.out.println("1");
////                channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
//////                channel.basicPublish(EXCHANGE_NAME,ROUTING_KEY,new AMQP.BasicProperties.Builder().contentEncoding("utf-8").build(),"".getBytes());
////                System.out.println("2");
//////                    channel.close();
//////                    connection.close();
////                }
////            } catch (IOException e) {
////                e.printStackTrace();
////            } catch (TimeoutException e) {
////                e.printStackTrace();
////            }
////        }).start();

//        new Thread(()->{
//            ConnectionFactory factory = new ConnectionFactory();
//            factory.setHost(IP_ADDRESS);
//            factory.setPort(PORT);
//            factory.setUsername("guest");
//            factory.setPassword("guest");
//            try {
//                Connection connection = factory.newConnection();
//                Channel channel = connection.createChannel();
//                channel.basicQos(64);
//                Consumer consumer = new DefaultConsumer(channel){
//                    @Override
//                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//                        System.out.println(new String(body));
////                            TimeUnit.SECONDS.sleep(1);
//                        channel.basicAck(envelope.getDeliveryTag(),false);
//                    }
//                };
////                channel.basicConsume(QUEUE_NAME,consumer);
//                GetResponse response = channel.basicGet(QUEUE_NAME, true);
//                System.out.println(response.toString());
//                TimeUnit.SECONDS.sleep(5);
////                channel.close();
////                connection.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (TimeoutException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
}
