package top.catoy.job;

import com.alibaba.fastjson.JSONObject;
import org.apache.xpath.operations.Bool;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.utilities.Assert;
import top.catoy.dao.ResultMapper;
import top.catoy.entity.Response;
import top.catoy.entity.Script;
import top.catoy.entity.TaskResult2;
import top.catoy.rpc.AsyncRPCClient;
import top.catoy.scriptExecution.entity.TaskResult;
import top.catoy.service.CompilationService;
import top.catoy.service.ScriptService;
import top.catoy.service.TaskService;
import top.catoy.scriptExecution.entity.Task;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @ClassName SpiderJob
 * @Description TODO
 * @Author admin
 * @Date 2020-04-11 21:01
 * @Version 1.0
 **/
@Service
public class SpiderJob implements BaseJob {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaskService taskService;

    @Autowired
    private ScriptService scriptService;

    @Autowired
    private CompilationService compilationService;

    @Autowired
    private AsyncRPCClient asyncRPCClient;

    @Autowired
    private ResultMapper resultMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("开始执行spiderJob++++++++++++++++++++++++++++++++++");
        String taskId = context.getJobDetail().getJobDataMap().getString("taskId");
        logger.info("RPCClient发送任务++++++++++++++++++++++++++++++++++");
        Future<String> result = asyncRPCClient.sendWithFixedReplay(taskId);
        try {
            String resultMessage = result.get();
            if (resultMessage == null) {
                logger.info("抓取任务超时++++++++++++++++++++++++++++++++++");
                TaskResult2 taskResult = new TaskResult2(null,null,"false","false","超时");
                resultMapper.addResult(taskResult);
                taskService.updateTaskRecentStatusByJobName(2,taskId);
            } else {
                logger.info("获取抓取结果++++++++++++++++++++++++++++++++++");
                TaskResult taskResult = JSONObject.parseObject(resultMessage, TaskResult.class);
                System.out.println(taskResult.toString());
                if (taskResult.getInvokeSuccess() == null) {
                    taskResult.setInvokeSuccess(false);
                }
                if (taskResult.getCompileSuccess() == null) {
                    taskResult.setCompileSuccess(false);
                }
                TaskResult2 taskResult2 = new TaskResult2(taskResult.getResultData()== null?"":JSONObject.toJSONString(taskResult.getResultData()),taskResult.getResultPrint() == null?null:taskResult.getResultPrint().toString(),taskResult.getCompileSuccess().toString(),taskResult.getInvokeSuccess().toString(),taskResult.getErrorMsg() == null?null:taskResult.getErrorMsg().toString());
                taskResult2.setTaskId(taskId);
                resultMapper.addResult(taskResult2);
                int recentStatus = -1;//-1没有执行过，0成功，1失败，2超时
                if (Boolean.valueOf(taskResult.getCompileSuccess()) && Boolean.valueOf(taskResult.getInvokeSuccess())) {
                    recentStatus = 0;
                } else {
                    recentStatus = 1;
                }
                taskService.updateTaskRecentStatusByJobName(recentStatus,taskId);
                logger.info("success receive response:{}",taskResult.toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
