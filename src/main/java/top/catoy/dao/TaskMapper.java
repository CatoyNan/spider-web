package top.catoy.dao;

import top.catoy.entity.Task;

/**
 * @ClassName TaskMapper
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 01:35
 * @Version 1.0
 **/
public interface TaskMapper {
    void addTask(Task task);

    Task getTaskByJobName(String jobName);

    void deleteJobByJobName(String jobName);

    void updateTaskStatusByJobName(int status,String jobName);

    void updateTaskRecentStatusByJobName(int recentStatus,String jobName);

}
