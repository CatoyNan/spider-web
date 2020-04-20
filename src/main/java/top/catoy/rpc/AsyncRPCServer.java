package top.catoy.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import top.catoy.entity.Response;
import top.catoy.entity.Script;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.service.CompilationService;
import top.catoy.service.ScriptService;
import top.catoy.service.TaskService;

import java.util.Arrays;

import static top.catoy.config.RabbitMQConfig.QUEUE_ASYNC_RPC;
import static top.catoy.config.RabbitMQConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY;

@Component
public class AsyncRPCServer {

	@Autowired
	AmqpTemplate amqpTemplate;

	@Autowired
	AsyncTask asyncTask;

	@Autowired
	TaskService taskService;

	@Autowired
	ScriptService scriptService;

	@Autowired
	CompilationService compilationService;

	Logger logger = LoggerFactory.getLogger(getClass());

//	@RabbitListener(queues = QUEUE_ASYNC_RPC)
//	public void process(String message, @Header(AmqpHeaders.REPLY_TO) String replyTo) {
//		logger.info("recevie message {} and reply to {}", message, replyTo);
//		if(replyTo.startsWith("amq.rabbitmq.reply-to")) {
//			logger.debug("starting with version 3.4.0, the RabbitMQ server now supports Direct reply-to");
//		}else {
//			logger.info("fall back to using a temporary reply queue");
//		}
//		ListenableFuture<String> asyncResult = asyncTask.expensiveOperation(message);
//		asyncResult.addCallback(new ListenableFutureCallback<String>() {
//			@Override
//			public void onSuccess(String result) {
//				amqpTemplate.convertAndSend(replyTo, result);
//			}
//
//			@Override
//			public void onFailure(Throwable ex) {
//
//			};
//		});
//	}

	@RabbitListener(queues = QUEUE_ASYNC_RPC_WITH_FIXED_REPLY)
	public void process(String message, @Header(AmqpHeaders.REPLY_TO) String replyTo,
			@Header(AmqpHeaders.CORRELATION_ID) String correlationId) {
//		logger.info("use a fixed reply queue {}, it is necessary to provide correlation data {} so that replies can be correlated to requests", replyTo, new String(correlationId));
		logger.info("RPCServer收到任务++++++++++++++++++++++++++++++++++");
		logger.info("receive taskId:{}",message);
		ListenableFuture<String> asyncResult = asyncTask.expensiveOperation(message);
		asyncResult.addCallback(new ListenableFutureCallback<String>() {
			@Override
			public void onSuccess(String result) {
				logger.info("ListenableFuture onSuccess++++++++++++++++++++++++++++++++++");
				logger.info(result.toString());
				amqpTemplate.convertAndSend(replyTo, (Object) result, new MessagePostProcessor() {
					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setCorrelationId(correlationId);
						return message;
					}
				});
			}

			@Override
			public void onFailure(Throwable ex) {
				amqpTemplate.convertAndSend(replyTo, (Object) "error", new MessagePostProcessor() {
					@Override
					public Message postProcessMessage(Message message) throws AmqpException {
						message.getMessageProperties().setCorrelationId(correlationId);
						return message;
					}
				});
			};
		});
	}

}