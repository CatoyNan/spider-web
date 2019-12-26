package top.catoy.scriptExecution.Processor;

import top.catoy.scriptExecution.entity.TaskResult;
import top.catoy.scriptExecution.entity.Task;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName InvokeProsser
 * @Description TODO
 * @Author admin
 * @Date 2019-12-17 16:47
 * @Version 1.0
 **/
public interface InvokeProsser {
    /**
     * 反射执行加载的类
     * @param task
     * @return
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    TaskResult run(Task task) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException;
}
