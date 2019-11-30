package top.catoy.compile.Service;

import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;

import java.lang.reflect.InvocationTargetException;

/**
 * @ClassName CompilationService
 * @Description TODO
 * @Author admin
 * @Date 2019-11-29 14:25
 * @Version 1.0
 **/
public interface CompilationService {

    /**
     * 编译
     * @param compilationTask
     * @return
     */
     CompileResponse compile(CompilationTask compilationTask);

    /**
     * 执行类文件
     * @param cls 类
     * @param methodName 方法名
     * @param paramsCls 方法参数类型
     * @param params 方法参数
     * @return
     */
     Object execute(Class<?> cls,String methodName,Class<?>[] paramsCls,Object[] params) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

}
