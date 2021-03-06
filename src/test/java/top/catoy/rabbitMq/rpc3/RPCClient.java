package top.catoy.rabbitMq.rpc3;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

import static top.catoy.config.RabbitMQConfig.QUEUE_SYNC_RPC;

@Component
public class RPCClient {

	@Autowired
	AmqpTemplate amqpTemplate;

	public String send(String message) {
		String result = (String) amqpTemplate.convertSendAndReceive(QUEUE_SYNC_RPC, message);
		return result;
	}

	@Async
	public Future<String> sendAsync(String message) {
		return new AsyncResult<String>(send(message));
	}
}