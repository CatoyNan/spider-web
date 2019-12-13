package top.catoy.compile.Service;

import top.catoy.compile.entity.CompilationResult;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;

import java.io.UnsupportedEncodingException;
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
     * @param compilationTask
     * @return
     */
     CompilationResult execute(CompilationTask compilationTask) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnsupportedEncodingException;

}
