package top.catoy.scriptExecution.Processor;

import top.catoy.scriptExecution.entity.Task;

import java.util.ArrayList;

public interface CompileProcessor {

    /**
     * 执行
     * @return
     */
    Class<?> run();

    /**
     * 设置编译参数
     */
    void setOptions();

    /**
     * 设置文件源
     * @param source
     */
    void setSource(StringBuffer source);

    /**
     * 设置编译任务
     * @param compilationTask
     */
    void setCompilationTask(Task compilationTask);

    /**
     * 设置类所要用到的参数
     * @param args
     */
    void setArgs(ArrayList<Object> args);


    /**
     * 执行编译并返回编译后的class文件
     * @param filePath
     * @param source
     * @param className
     * @param ops
     * @return
     */
    Class<?> loadClass(String filePath, StringBuffer source, String className, ArrayList<String> ops) throws ClassNotFoundException;

}
