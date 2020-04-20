package top.catoy.service;

import top.catoy.entity.Task;

/**
 * @ClassName TaskService
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 01:33
 * @Version 1.0
 **/
public interface TaskService {
    void addTask(Task task);

    void deleteJobByJobName(String jobName);

    void updateTaskStatusByJobName(int status,String jobName);

    Task getTaskByJobName(String jobName);

    void updateTaskRecentStatusByJobName(int recentStatus,String jobName);
}
