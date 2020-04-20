package top.catoy.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName RabbitMQConfig
 * @Description TODO
 * @Author admin
 * @Date 2020-01-08 16:21
 * @Version 1.0
 **/
@EnableRabbit
@Configuration
public class RabbitMQConfig {
//    //RabbitMQ的配置信息
//    @Value("${spring.rabbitmq.host}")
//    private String host;
//    @Value("${spring.rabbitmq.port}")
//    private Integer port;
//    @Value("${spring.rabbitmq.username}")
//    private String username;
//    @Value("${spring.rabbitmq.password}")
//    private String password;
//    @Value("${spring.rabbitmq.virtual-host}")
//    private String virtualHost;
//
//    private static final String EXCHANGE_NAME = "spider.exchange";
//    private static final String RPC_QUEUE_NAME = "rpc_queue";
//    private static final String REPLY_QUEUE_NAME = "reply_queue";
//    private static final String ROUTINGKEY = "spider.queue";
//
//    //建立一个连接容器，类型数据库的连接池
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory =
//                new CachingConnectionFactory(host, port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//        connectionFactory.setVirtualHost(virtualHost);
//        connectionFactory.setPublisherConfirms(true);// 确认机制
//        ExecutorService service= Executors.newFixedThreadPool(20);//20个线程的线程池
//        connectionFactory.setExecutor(service);
//        //发布确认，template要求CachingConnectionFactory的publisherConfirms属性设置为true
//        return connectionFactory;
//    }
//
//    // RabbitMQ的使用入口
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)//必须是prototype类型
//    public RabbitTemplate rpcTemplate() {
//        RabbitTemplate template = new RabbitTemplate(this.connectionFactory());
////        template.setMessageConverter(this.jsonMessageConverter());
//        template.setMandatory(true);
//        template.setExchange(exchange().getName());
//        template.setRoutingKey(ROUTINGKEY);
//        template.setDefaultReceiveQueue(rpcQueue().getName());
////        template.setReplyAddress(exchange().getName() + "/" + replyQueue().getName());
//        template.setReplyTimeout(60000);
//        return template;
//    }
//
//    /**
//     * 交换机
//     * 针对消费者配置
//     * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
//     * HeadersExchange ：通过添加属性key-value匹配
//     * DirectExchange:按照routingkey分发到指定队列
//     * TopicExchange:多关键字匹配
//     */
//    @Bean
//    public DirectExchange exchange() {
//        return new DirectExchange(EXCHANGE_NAME,true,false);
//    }
//
//    /**
//     * 请求队列
//     *
//     * @return
//     */
//    @Bean
//    public Queue rpcQueue() {
//        return new Queue(RPC_QUEUE_NAME, true); //队列持久
//
//    }
//
//    /**
//     * 返回队列
//     * @return
//     */
//    @Bean
//    public Queue replyQueue() {
//        return new Queue(REPLY_QUEUE_NAME, true); //队列持久
//
//    }
//    /**
//     * 请求队列和交换机绑定
//     * @return
//     */
//    @Bean
//    public Binding requestBinding(Queue rpcQueue, DirectExchange exchange) {
//        return BindingBuilder.bind(rpcQueue).to(exchange).with(RabbitMQConfig.ROUTINGKEY);
//    }
//
//    /**
//     * 返回队列和交换机绑定
//     * @param replyQueue
//     * @param exchange
//     * @return
//     */
//    @Bean
//    public Binding replyBinding(Queue replyQueue, DirectExchange exchange) {
//        return BindingBuilder.bind(replyQueue).to(exchange).with(RabbitMQConfig.ROUTINGKEY);
//    }
//
//    @Bean
//    public MessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    /**
//     * 声明一个监听容器
//     *
//     * @return
//     */
////    @Bean(name="rabbitListenerContainer")
////    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
////        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
////        factory.setMessageConverter(jsonMessageConverter());
////        factory.setConnectionFactory(connectionFactory());
////        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置消费者手动应答
////        factory.setPrefetchCount(1);//从代理接收消息的数目。这个值越大，发送消息就越快，但是导致非顺序处理的风险就越高
////
////        return factory;
////    }
//
////    @Bean
////    Receiver receiver(){
////        return new Receiver();
////    }
////
////    @Bean
////    MessageListenerAdapter listenerAdapter(Receiver receiver) {
////        return new MessageListenerAdapter(receiver, "onMessage");
////    }
////
//
//    @Bean
//    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate rabbitTemplate, SimpleMessageListenerContainer container) {
//        return new AsyncRabbitTemplate(rabbitTemplate, container);
//    }
//    @Bean
//    public SimpleMessageListenerContainer messageListenerContainer() {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory());
//        container.setQueues(replyQueue());
//        container.setQueueNames(replyQueue().getName());
//        container.setMessageListener(rpcTemplate());
//        ExecutorService executorService=Executors.newFixedThreadPool(300);  //300个线程的线程池
//        container.setTaskExecutor(executorService);
//        container.setConcurrentConsumers(200);
//        container.setPrefetchCount(5);
//        return container;
//    }
//
//
//
//    @Bean
//    public CharacterEncodingFilter characterEncodingFilter() {
//        CharacterEncodingFilter filter = new CharacterEncodingFilter();
//        filter.setEncoding("UTF-8");
//        filter.setForceEncoding(true);
//        return filter;
//    }

    /**
     * 同步RPC队列
     */
    public static final String QUEUE_SYNC_RPC = "rpc.sync";

    /**
     * 异步RPC队列，使用临时回复队列，或者使用“Direct reply-to”特性
     */
    public static final String QUEUE_ASYNC_RPC = "rpc.async";

    /**
     * 异步RPC队列，每个客户端使用不同的固定回复队列，需要额外提供correlationId以关联请求和响应
     */
    public static final String QUEUE_ASYNC_RPC_WITH_FIXED_REPLY = "rpc.with.fixed.reply";

    @Bean
    public Queue syncRPCQueue() {
        return new Queue(QUEUE_SYNC_RPC);
    }

    @Bean
    public Queue asyncRPCQueue() {
        return new Queue(QUEUE_ASYNC_RPC);
    }

    @Bean
    public Queue fixedReplyRPCQueue() {
        return new Queue(QUEUE_ASYNC_RPC_WITH_FIXED_REPLY);
    }

    @Bean
    public Queue repliesQueue() {
        return new AnonymousQueue();
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueueNames(repliesQueue().getName());
        return container;
    }

    @Bean
    public AsyncRabbitTemplate asyncRabbitTemplate(RabbitTemplate template, SimpleMessageListenerContainer container) {
        AsyncRabbitTemplate asyncRabbitTemplate = new AsyncRabbitTemplate(template, container);
        asyncRabbitTemplate.setReceiveTimeout(3600000);
        return asyncRabbitTemplate;
    }
}
