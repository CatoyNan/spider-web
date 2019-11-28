package top.catoy.compile.compileProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.util.ClassUtil;
import top.catoy.exception.CompileException;
import top.catoy.exception.CompileExceptionStatusEnum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName DefaultCompileProcessor
 * @Description TODO
 * @Author admin
 * @Date 2019-11-22 16:20
 * @Version 1.0
 **/
public class DefaultCompileProcessor implements CompileProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DefaultCompileProcessor.class);
    private ArrayList<String> ops = new ArrayList<String>();//编译参数
    private CompilationTask compilationTask;
//    private StringBuffer source = new StringBuffer();//编译源
//    private Map<String, Object> args = new HashMap<>();//类所要用到的参数
//    private String filePath = "";
//    private String className = "";

    @Override
    public Class<?> run() throws CompileException{
        setOptions();
        if (compilationTask.getSource() == null || compilationTask.getSource().length() == 0) {
            logger.error("source is not valid");
            throw new CompileException(CompileExceptionStatusEnum.SOURCE_IS_NOT_VALID.getCode(),
                    CompileExceptionStatusEnum.SOURCE_IS_NOT_VALID.getMessage());
        }

        if (compilationTask.getRootPath() == null || "".equals(compilationTask.getRootPath()) || compilationTask.getRootPath().length() == 0) {
            logger.error("file path is not vaild");
            throw new CompileException(CompileExceptionStatusEnum.FILEPATH_NOT_VALID.getCode(),
                    CompileExceptionStatusEnum.FILEPATH_NOT_VALID.getMessage());
        }

        if (compilationTask.getClassName() == null || "".equals(compilationTask.getClassName()) || compilationTask.getClassName().length() == 0 || !compilationTask.getClassName().endsWith(".java")) {
            logger.error("file path is not vaild");
            throw new CompileException(CompileExceptionStatusEnum.CLASSNAME_IS_NOT_VALID.getCode(),
                    CompileExceptionStatusEnum.CLASSNAME_IS_NOT_VALID.getMessage());
        }

        return loadClass(compilationTask.getRootPath(), compilationTask.getSource(), compilationTask.getClassName(), ops);
    }

    @Override
    public void setOptions() {
        ops.add("-Xlint:unchecked");
    }

    @Override
    public void setSource(StringBuffer source) {
        compilationTask.setSource(source);
    }

    @Override
    public void setArgs(Map<String, Object> args) {
        compilationTask.setData(args);
    }

    @Override
    public Class<?> loadClass(String filePath, StringBuffer source, String className, ArrayList<String> ops) {
        return ClassUtil.loadClass(filePath,new String(source),className,ops);
    }

    public CompilationTask getCompilationTask() {
        return compilationTask;
    }

    public void setCompilationTask(CompilationTask compilationTask) {
        this.compilationTask = compilationTask;
    }

}
