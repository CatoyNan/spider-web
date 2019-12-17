package top.catoy.compile.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.catoy.compile.Service.CompilationService;
import top.catoy.compile.compileProcessor.CompileProcessor;
import top.catoy.compile.compileProcessor.DefaultInvokeProcessor;
import top.catoy.compile.entity.TaskResult;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;
import top.catoy.compile.enums.ResponseStatusEnum;
import top.catoy.compile.util.ClassUtil;
import top.catoy.exception.CompileException;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
    public CompileResponse execute(CompilationTask compilationTask) {
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        defaultCompileProcessor.setCompilationTask(compilationTask);
        Class<?> cls;
        TaskResult taskResult;
        try {
            cls = defaultCompileProcessor.run();
            logger.info("compile success");
            compilationTask.setClz(cls);
            taskResult = defaultInvokeProcessor.run(compilationTask);
            logger.info("execute success");
            return new CompileResponse(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMessage(), taskResult);
        } catch (IllegalArgumentException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.FAIL.getCode(), ResponseStatusEnum.FAIL.getMessage(), new TaskResult(null,null,true,false,new StringBuffer(e.getMessage())));
        } catch (CompileException e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.FAIL.getCode(), ResponseStatusEnum.FAIL.getMessage(), new TaskResult(null, null, false, false, new StringBuffer(e.getMessage())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.UNKNOWN_ERROR.getCode(), ResponseStatusEnum.UNKNOWN_ERROR.getMessage(), null);
        }
    }
}
