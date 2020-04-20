package top.catoy.rpc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import top.catoy.entity.Response;
import top.catoy.entity.Script;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.entity.TaskResult;
import top.catoy.service.CompilationService;
import top.catoy.service.ScriptService;
import top.catoy.service.TaskService;

import java.util.Arrays;

@Component
public class AsyncTask {

	Logger logger = LoggerFactory.getLogger(getClass());

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

	@Async
	public ListenableFuture<String> expensiveOperation(String message) {
		top.catoy.entity.Task t = taskService.getTaskByJobName(message);
		if (t == null) {
			return new AsyncResult<String>("");
		}
		top.catoy.scriptExecution.entity.Task task = new Task();
		Script script = scriptService.getScriptById(t.getScriptId());
		task.setClassName(script.getClassName());
		task.setMethodName(script.getMethodName());
		task.setSource(new StringBuffer(script.getScriptContent()));
		task.setArgs(Arrays.asList(script.getMethodArgs().split(",")));
//		logger.info("compilationTask={}",task);
		logger.info("开始执行爬虫抓取任务++++++++++++++++++++++++++++++++++");
		TaskResult taskResult = compilationService.execute2(task);
		logger.info("结束爬虫抓取任务++++++++++++++++++++++++++++++++++");
		logger.info("开始返回抓取任务结果++++++++++++++++++++++++++++++++++");
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("resultData",taskResult.getResultData());
		jsonObject.put("resultPrint",taskResult.getResultPrint());
		jsonObject.put("isCompileSuccess",taskResult.getCompileSuccess());
		jsonObject.put("isInvokeSuccess",taskResult.getInvokeSuccess());
		jsonObject.put("errorMsg",taskResult.getErrorMsg());
		return new AsyncResult<String>(jsonObject.toJSONString());
	}
}