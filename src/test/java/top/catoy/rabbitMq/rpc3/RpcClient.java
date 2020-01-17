package top.catoy.rabbitMq.rpc3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName Producer
 * @Description TODO
 * @Author admin
 * @Date 2020-01-13 16:29
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest()
public class RpcClient {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    DirectExchange exchange;


    @Test
    public void test() {
        rabbitTemplate.convertSendAndReceive(exchange.getName(),);
    }

}
