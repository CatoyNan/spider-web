package top.catoy.service.Impl;

import com.sun.jmx.snmp.tasks.TaskServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.catoy.dao.TaskMapper;
import top.catoy.entity.Task;
import top.catoy.service.TaskService;

/**
 * @ClassName TaskServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 01:34
 * @Version 1.0
 **/
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskMapper taskMapper;

    @Override
    public void addTask(Task task) {
        taskMapper.addTask(task);
    }

    @Override
    public void deleteJobByJobName(String jobName) {
        taskMapper.deleteJobByJobName(jobName);
    }

    @Override
    public void updateTaskStatusByJobName(int status,String jobName) {
        taskMapper.updateTaskStatusByJobName(status,jobName);
    }

    @Override
    public Task getTaskByJobName(String jobName) {
        return taskMapper.getTaskByJobName(jobName);
    }

    @Override
    public void updateTaskRecentStatusByJobName(int recentStatus, String jobName) {
        taskMapper.updateTaskRecentStatusByJobName(recentStatus,jobName);
    }
}
