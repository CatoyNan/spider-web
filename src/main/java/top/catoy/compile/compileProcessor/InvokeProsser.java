package top.catoy.compile.compileProcessor;

import top.catoy.compile.entity.TaskResult;
import top.catoy.compile.entity.CompilationTask;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName InvokeProsser
 * @Description TODO
 * @Author admin
 * @Date 2019-12-17 16:47
 * @Version 1.0
 **/
public interface InvokeProsser {
    TaskResult run(CompilationTask compilationTask) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException;
}
