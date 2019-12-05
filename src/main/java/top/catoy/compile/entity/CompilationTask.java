package top.catoy.compile.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CompilationTask
 * @Description TODO
 * @Author admin
 * @Date 2019-11-27 19:35
 * @Version 1.0
 **/
public class CompilationTask {
    //类名
    private String className;

    //存储根路径
    private String rootPath;

    //文件源
    private StringBuffer source;

    //方法参数
    private ArrayList<Object> args;

    //入口方法
    private String methodName;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public StringBuffer getSource() {
        return source;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setSource(StringBuffer source) {
        this.source = source;
    }

    public ArrayList<Object> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<Object> args) {
        this.args = args;
    }
}
