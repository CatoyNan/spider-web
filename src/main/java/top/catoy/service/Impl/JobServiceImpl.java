package top.catoy.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.catoy.dao.JobAndTriggerMapper;
import top.catoy.dao.TaskMapper;
import top.catoy.entity.JobAndTrigger;
import top.catoy.entity.Script;
import top.catoy.entity.Task;
import top.catoy.entity.TaskDto;
import top.catoy.service.JobService;
import top.catoy.service.ScriptService;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName JobServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 03:26
 * @Version 1.0
 **/
@Service
public class JobServiceImpl implements JobService {
    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private ScriptService scriptService;

    @Override
    public PageInfo<TaskDto> queryJob(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<JobAndTrigger> jobAndTriggerList = jobAndTriggerMapper.getJobAndTriggerDetails();
        List<TaskDto> taskDtoList = new ArrayList();
        for (JobAndTrigger j:jobAndTriggerList) {
            TaskDto taskDto = new TaskDto();
            String jobName = j.getJOB_NAME();
            Task task = taskMapper.getTaskByJobName(jobName);
            if(task == null){
                continue;
            }
            Script script = scriptService.getScriptById(task.getScriptId());
            taskDto.setId(task.getId());
            taskDto.setScriptId(task.getScriptId());
            taskDto.setCallBack(task.getCallBack());
            taskDto.setCronExpression(task.getCronExpression());
            taskDto.setGmtCreate(task.getGmtCreate());
            taskDto.setScriptName(script.getScriptName());
            taskDto.setUrl(task.getUrl());
            taskDto.setJobGroup(task.getJobGroup());
            taskDto.setTaskStatus(task.getTaskStatus());
            taskDto.setRecentStatus(task.getRecentStatus());
            taskDtoList.add(taskDto);
        }
        PageInfo<TaskDto> page = new PageInfo(taskDtoList);
        return page;
    }
}
