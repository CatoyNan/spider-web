package top.catoy.rabbitMq.rpc3;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static top.catoy.config.RabbitMQConfig.QUEUE_SYNC_RPC;

@Component
@RabbitListener(queues = QUEUE_SYNC_RPC)
public class RPCServer {

	@RabbitHandler
	public String process(String message) {
		int millis = (int) (Math.random() * 2 * 1000);
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
		return message + " sleep for " + millis + " ms";
	}

}