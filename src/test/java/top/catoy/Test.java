package top.catoy;

import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName Test
 * @Description TODO
 * @Author admin
 * @Date 2019-12-14 22:53
 * @Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest()
public class Test {
    @Autowired
    private RabbitTemplate template;

    @org.junit.Test
    public void hello() {
        template.convertSendAndReceive("","rpc_queue","hello");
    }

}
