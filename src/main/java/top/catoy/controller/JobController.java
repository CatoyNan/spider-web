package top.catoy.controller;

import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import top.catoy.entity.JobAndTrigger;
import top.catoy.entity.Response;
import top.catoy.entity.Task;
import top.catoy.entity.TaskDto;
import top.catoy.job.BaseJob;
import top.catoy.job.SpiderJob;
import top.catoy.job.TestJob;
import top.catoy.scriptExecution.enums.TaskResultStatusEnum;
import top.catoy.service.IJobAndTriggerService;
import top.catoy.service.JobService;
import top.catoy.service.TaskService;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/job")
public class JobController {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private JobService jobService;

    //加入Qulifier注解，通过名称注入bean
    @Autowired
    @Qualifier("Scheduler")
    private Scheduler scheduler;

    private static Logger log = LoggerFactory.getLogger(JobController.class);


    @PostMapping(value = "/addjob")
    public Response addjob(@RequestBody Task task) throws Exception {
        String time = String.valueOf(new Date().getTime());
        task.setJobClassName(SpiderJob.class.getCanonicalName());
        task.setJobGroup(time);
        task.setJobName(time);
        addJob(task);
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),null);
    }

    public void addJob(Task task) throws Exception {

        // 启动调度器  
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = JobBuilder.newJob(getClass(task.getJobClassName()).getClass()).withIdentity(task.getJobGroup(), task.getJobGroup()).usingJobData("taskId",String.valueOf(task.getJobGroup())).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());

        //构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(task.getJobClassName(), task.getJobGroup())
                .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.getContext().put("time",new Date().getTime());
            taskService.addTask(task);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }


    @GetMapping(value = "/pausejob")
    public Response pausejob(@RequestParam(value = "jobClassName",defaultValue = "top.catoy.job.SpiderJob") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobPause(jobClassName, jobGroupName);
        taskService.updateTaskStatusByJobName(1,jobGroupName);
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),null);
    }

    public void jobPause(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    @GetMapping(value = "/resumejob")
    public Response resumejob(@RequestParam(value = "jobClassName", defaultValue = "top.catoy.job.SpiderJob") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobresume(jobClassName, jobGroupName);
        taskService.updateTaskStatusByJobName(0,jobGroupName);
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),null);
    }

    public void jobresume(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeTrigger(TriggerKey.triggerKey(jobClassName,jobGroupName));
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    @PostMapping(value = "/reschedulejob")
    public void rescheduleJob(@RequestParam(value = "jobClassName") String jobClassName,
                              @RequestParam(value = "jobGroupName") String jobGroupName,
                              @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        jobreschedule(jobClassName, jobGroupName, cronExpression);
    }

    public void jobreschedule(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }


    @GetMapping(value = "/deletejob")
    public Response deletejob(@RequestParam(value = "jobClassName",defaultValue = "top.catoy.job.SpiderJob") String jobClassName, @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        jobdelete(jobClassName, jobGroupName);
        taskService.deleteJobByJobName(jobGroupName);
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),null);
    }

    public void jobdelete(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }


    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(@RequestParam(value = "pageNum") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize) {
        PageInfo<TaskDto> taskDtoPageInfo = jobService.queryJob(pageNum, pageSize);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("taskDtoPageInfo", taskDtoPageInfo);
        map.put("number", taskDtoPageInfo.getTotal());
        return map;
    }

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

//    public static TestJob getClass(String classname) throws Exception {
//        Class<?> class1 = Class.forName(classname);
//        return (TestJob) TestJob.class.newInstance();
//    }


}
