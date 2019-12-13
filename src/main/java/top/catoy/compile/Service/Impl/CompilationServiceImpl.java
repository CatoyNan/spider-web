package top.catoy.compile.Service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import top.catoy.compile.Service.CompilationService;
import top.catoy.compile.compileProcessor.CompileProcessor;
import top.catoy.compile.entity.CompilationResult;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;
import top.catoy.compile.enums.ResponseStatusEnum;
import top.catoy.compile.util.ClassUtil;
import top.catoy.exception.CompileException;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
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

    @Override
    public CompileResponse compile(CompilationTask compilationTask) {
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        defaultCompileProcessor.setCompilationTask(compilationTask);
        Class<?> cls;
        try {
            cls = defaultCompileProcessor.run();
        } catch (CompileException e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.COMPILE_ERROR.getCode(), ResponseStatusEnum.COMPILE_ERROR.getMessage(), new CompilationResult(null, null, false, false, new StringBuffer(e.getCode() + e.getMessage())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.UNKNOWN_ERROR.getCode(), ResponseStatusEnum.UNKNOWN_ERROR.getMessage(), null);
        }
        logger.info("compile success");
        CompilationResult compilationResult;
        compilationTask.setClz(cls);
        try {
            compilationResult = execute(compilationTask);
            logger.info("execute success");
        } catch (IllegalArgumentException|InvocationTargetException|NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.INVOKE_ERROR.getCode(), ResponseStatusEnum.INVOKE_ERROR.getMessage(), new CompilationResult(null,null,true,false,new StringBuffer(e.getMessage())));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new CompileResponse(ResponseStatusEnum.UNKNOWN_ERROR.getCode(), ResponseStatusEnum.UNKNOWN_ERROR.getMessage(), new CompilationResult(null,null,true,true,null));
        }
        return new CompileResponse(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMessage(), compilationResult);
    }

    @Override
    public CompilationResult execute(CompilationTask compilationTask) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException {
        //执行测试方法
        String methodName = compilationTask.getMethodName();
        Method[] ms = compilationTask.getClz().getMethods();
        Class<?> returnType = null;
        Class<?>[] paramTypes = null;
        StringBuffer resulPrint = new StringBuffer();
        Object[] params = compilationTask.getArgs().toArray();
        for (int i = 0; i < ms.length; i++) {
            if (ms[i].getName().equals(methodName)) {
                // 得到方法的返回值类型的类类型
                returnType = ms[i].getReturnType();
                System.out.print(returnType.getName() + " ");
                // 获取参数类类型数组
                paramTypes = ms[i].getParameterTypes();
                for (Class class2 : paramTypes) {
                    System.out.print(class2.getName() + ",");
                }
                System.out.println(")");
                break;
            }
        }
        Object resultData = ClassUtil.invoke(compilationTask.getClz(), methodName, paramTypes, params, resulPrint);
        return new CompilationResult(resultData, resulPrint, true, true, null);
    }
}
