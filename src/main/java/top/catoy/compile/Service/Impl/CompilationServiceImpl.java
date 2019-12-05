package top.catoy.compile.Service.Impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import top.catoy.compile.Service.CompilationService;
import top.catoy.compile.compileProcessor.CompileProcessor;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;
import top.catoy.compile.enums.CompileExceptionStatusEnum;
import top.catoy.compile.enums.ResponseStatusEnum;
import top.catoy.compile.util.ClassUtil;
import top.catoy.exception.CompileException;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @ClassName CompilationServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2019-11-29 14:29
 * @Version 1.0
 **/
@Service
public class CompilationServiceImpl implements CompilationService {
    private static final Logger logger = Logger.getLogger(CompilationServiceImpl.class);
    @Resource(name = "defaultCompileProcessor")
    private CompileProcessor defaultCompileProcessor;

    @Override
    public CompileResponse compile(CompilationTask compilationTask) {
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        defaultCompileProcessor.setCompilationTask(compilationTask);
        Class<?> cls = null;
        try {
            cls = defaultCompileProcessor.run();
        } catch (CompileException e) {
            logger.error("CompileException",e);
            if (e.getCode() == CompileExceptionStatusEnum.SOURCE_COMPILE_ERROR.getCode() ||
                    e.getCode() == CompileExceptionStatusEnum.INVOKE_Target_Exception.getCode()) {
                return new CompileResponse(ResponseStatusEnum.COMPILE_ERROR.getCode(), ResponseStatusEnum.COMPILE_ERROR.getMessage(), e.getMessage());
            } else if (e.getCode() == CompileExceptionStatusEnum.CLASSNAME_IS_NOT_VALID.getCode() ||
                    e.getCode() == CompileExceptionStatusEnum.FILEPATH_NOT_VALID.getCode() ||
                    e.getCode() == CompileExceptionStatusEnum.SOURCE_IS_NOT_VALID.getCode()) {
                return new CompileResponse(ResponseStatusEnum.COMPILE_ERROR.getCode(), ResponseStatusEnum.COMPILE_ERROR.getMessage(), null);
            } else {
                return new CompileResponse(ResponseStatusEnum.UNKNOWN_ERROR.getCode(), ResponseStatusEnum.UNKNOWN_ERROR.getMessage(), null);
            }
        }

        Object result = null;

        try {
            //执行测试方法
            String methodName = compilationTask.getMethodName();
            Method[] ms = cls.getMethods();
            Class<?> returnType = null;
            Class<?>[] paramTypes  = null;
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
            result = execute(cls, methodName, paramTypes, compilationTask.getArgs().toArray());
        } catch (IllegalArgumentException e) {
            logger.error(e.toString(), e);
            return new CompileResponse(ResponseStatusEnum.INVOKE_ERROR.getCode(), ResponseStatusEnum.INVOKE_ERROR.getMessage(), e.getMessage());
        } catch (InvocationTargetException e) {
            logger.error(e.toString(), e);
            return new CompileResponse(ResponseStatusEnum.INVOKE_ERROR.getCode(), ResponseStatusEnum.INVOKE_ERROR.getMessage(), e.getMessage());
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return new CompileResponse(ResponseStatusEnum.UNKNOWN_ERROR.getCode(), ResponseStatusEnum.UNKNOWN_ERROR.getMessage(), null);
        }
        return new CompileResponse(ResponseStatusEnum.SUCCESS.getCode(), ResponseStatusEnum.SUCCESS.getMessage(), result);
    }

    @Override
    public Object execute(Class<?> cls, String methodName, Class<?>[] paramsCls, Object[] params) throws
            InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException {
        return ClassUtil.invoke(cls, methodName, paramsCls, params);
    }
}