package top.catoy.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import top.catoy.service.ScriptService;

import java.io.Serializable;

public class TestJob implements Job, Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private ScriptService scriptService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println(scriptService);//注入jobService 执行相关业务操作
        System.out.println("任务执行成功");
    }
}