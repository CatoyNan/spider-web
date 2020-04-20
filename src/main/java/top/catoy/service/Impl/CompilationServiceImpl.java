package top.catoy.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.catoy.scriptExecution.Processor.CompileProcessor;
import top.catoy.scriptExecution.Processor.DefaultInvokeProcessor;
import top.catoy.scriptExecution.entity.TaskResult;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.enums.TaskResultStatusEnum;
import top.catoy.entity.Response;
import top.catoy.scriptExecution.exception.ExecutionException;
import top.catoy.service.CompilationService;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName CompilationServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2019-11-29 14:29
 * @Version 1.0
 **/
@Service
public class CompilationServiceImpl implements CompilationService {
    private static final Logger logger = LoggerFactory.getLogger(CompilationServiceImpl.class);
    @Resource(name = "defaultCompileProcessor")
    private CompileProcessor defaultCompileProcessor;
    @Resource(name = "defaultInvokeProcessor")
    private DefaultInvokeProcessor defaultInvokeProcessor;

    @Override
    public Response execute(Task compilationTask) {
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        defaultCompileProcessor.setCompilationTask(compilationTask);
        TaskResult taskResult;
        try {
            Class<?> cls;
            cls = defaultCompileProcessor.run();
            logger.info("script compile success");
            compilationTask.setClz(cls);
            taskResult = defaultInvokeProcessor.run(compilationTask);
            logger.info("script invoke success");
            return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(), taskResult);
        } catch (IllegalArgumentException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return new Response(TaskResultStatusEnum.FAIL.getCode(), TaskResultStatusEnum.FAIL.getMessage(), new TaskResult(null,null,true,false,new StringBuffer(e.getMessage())));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return new Response(TaskResultStatusEnum.FAIL.getCode(), TaskResultStatusEnum.FAIL.getMessage(), new TaskResult(null, null, false, false, new StringBuffer(e.getMessage())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new Response(TaskResultStatusEnum.UNKNOWN_ERROR.getCode(), TaskResultStatusEnum.UNKNOWN_ERROR.getMessage(), null);
        }
    }

    public TaskResult execute2(Task compilationTask) {
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        defaultCompileProcessor.setCompilationTask(compilationTask);
        TaskResult taskResult;
        try {
            Class<?> cls;
            cls = defaultCompileProcessor.run();
            logger.info("script compile success");
            compilationTask.setClz(cls);
            taskResult = defaultInvokeProcessor.run(compilationTask);
            logger.info("script invoke success");
            return taskResult;
        } catch (IllegalArgumentException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return  new TaskResult(null,null,true,false,new StringBuffer(e.getMessage()));
        } catch (ExecutionException e) {
            logger.error(e.getMessage(), e);
            return  new TaskResult(null, null, false, false, new StringBuffer(e.getMessage()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
