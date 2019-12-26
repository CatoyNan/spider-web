package top.catoy.scriptExecution.Processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.exception.ExecutionException;
import top.catoy.scriptExecution.util.ClassUtil;
import top.catoy.scriptExecution.enums.TaskExceptionStatusEnum;

import java.util.ArrayList;

/**
 * @ClassName DefaultCompileProcessor
 * @Description TODO
 * @Author admin
 * @Date 2019-11-22 16:20
 * @Version 1.0
 **/
@Component
public class DefaultCompileProcessor implements CompileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCompileProcessor.class);
    private ArrayList<String> ops = new ArrayList<String>();//编译参数
    private Task task;

    @Override
    public Class<?> run() throws ExecutionException {
        setOptions();
        paramCheck();
        return loadClass(task.getRootPath(), task.getSource(), task.getClassName(), ops);
    }

    @Override
    public void setOptions() {
        ops.add("-Xlint:unchecked");
    }

    @Override
    public void setSource(StringBuffer source) {
        task.setSource(source);
    }

    @Override
    public void setCompilationTask(Task compilationTask) {
        this.task = compilationTask;
    }

    @Override
    public void setArgs(ArrayList<Object> args) {
        task.setArgs(args);
    }

    @Override
    public Class<?> loadClass(String filePath, StringBuffer source, String className, ArrayList<String> ops) throws ExecutionException {
        return ClassUtil.loadClass(filePath,new String(source),className,ops);
    }

    /**
     * 参数校验
     * @throws ExecutionException
     */
    public void paramCheck() throws ExecutionException {
        if (task.getSource() == null || task.getSource().length() == 0) {
            throw new ExecutionException(TaskExceptionStatusEnum.SOURCE_IS_NOT_VALID.getCode(),
                    TaskExceptionStatusEnum.SOURCE_IS_NOT_VALID.getMessage());
        }

        if (task.getRootPath() == null || "".equals(task.getRootPath()) || task.getRootPath().length() == 0) {
            throw new ExecutionException(TaskExceptionStatusEnum.FILEPATH_NOT_VALID.getCode(),
                    TaskExceptionStatusEnum.FILEPATH_NOT_VALID.getMessage());
        }

        if (task.getClassName() == null || "".equals(task.getClassName()) || task.getClassName().length() == 0 || !task.getClassName().endsWith(".java")) {
            throw new ExecutionException(TaskExceptionStatusEnum.CLASSNAME_IS_NOT_VALID.getCode(),
                    TaskExceptionStatusEnum.CLASSNAME_IS_NOT_VALID.getMessage());
        }
    }

    public Task getCompilationTask() {
        return task;
    }
}
