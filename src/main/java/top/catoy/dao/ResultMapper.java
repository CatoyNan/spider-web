package top.catoy.dao;


import top.catoy.entity.TaskResult2;
import top.catoy.scriptExecution.entity.TaskResult;

/**
 * @ClassName TaskMapper
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 01:35
 * @Version 1.0
 **/
public interface ResultMapper {
    TaskResult getRecentResultByTaskId(int taskId);

    int addResult(TaskResult2 taskResult);
}
