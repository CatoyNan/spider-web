package top.catoy.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName SpiderJob
 * @Description TODO
 * @Author admin
 * @Date 2020-04-11 21:01
 * @Version 1.0
 **/
public class SpiderJob implements BaseJob{
    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.error(String.valueOf(context.getScheduler().getContext().get("time")));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
