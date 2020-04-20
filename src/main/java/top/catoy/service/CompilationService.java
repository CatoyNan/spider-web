package top.catoy.service;

import top.catoy.scriptExecution.entity.Task;
import top.catoy.entity.Response;
import top.catoy.scriptExecution.entity.TaskResult;

/**
 * @ClassName CompilationService
 * @Description TODO
 * @Author admin
 * @Date 2019-12-17 17:33
 * @Version 1.0
 **/
public interface CompilationService {
    /**
     * 编译执行
     * @param compilationTask
     * @return
     */
    Response execute(Task compilationTask);

    TaskResult execute2(Task compilationTask);

}
