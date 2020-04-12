package top.catoy.rabbitMq.rpc3;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.catoy.scriptExecution.exception.ExecutionException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RPCClientTest {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	RPCClient rpcClient;

	@Test
	public void testSend() {
		String result = rpcClient.send("徐剑楠");
		System.out.println("123");
		logger.error(result);
	}

	@Test
	public void testSendAsync() throws InterruptedException, ExecutionException, java.util.concurrent.ExecutionException {
		String[] messages = { "hello", "my", "name", "is", "leijun" };
		List<Future<String>> results = new ArrayList<>();
		for (String message : messages) {
			Future<String> result = rpcClient.sendAsync(message);
			results.add(result);
		}
		for (Future<String> future : results) {
			String result = future.get();
			if (result == null) {
				logger.info("message timeout after 5 seconds");
			} else {
				logger.info(result);
			}
		}
	}
}