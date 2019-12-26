package top.catoy.scriptExecution.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;

/**
 * @ClassName CompilationTask
 * @Description TODO
 * @Author admin
 * @Date 2019-11-27 19:35
 * @Version 1.0
 **/
@Component
public class Task {
    private Integer id;
    //类名
    @Pattern(regexp = ".*\\.java",message = "ClassName should be end with '.java'")
    @Pattern(regexp = ".*\\.[A-Z].*\\.java",message = "ClassName should be capital")
    @NotEmpty(message = "ClassName can not be empty")
    private String className;

    //存储根路径
    private String rootPath;

    //文件源
    @NotEmpty(message = "code can not be empty")
    private StringBuffer source;

    //编译后加载的类对象
    private Class<?> clz;

    //方法参数
    private ArrayList<Object> args;

    //入口方法
    @NotEmpty(message = "MethodName can not be empty")
    private String methodName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }

    @JsonProperty("code")
    public StringBuffer getSource() {
        return source;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @JsonProperty("code")
    public void setSource(StringBuffer source) {
        this.source = source;
    }

    public ArrayList<Object> getArgs() {
        return args;
    }

    public void setArgs(ArrayList<Object> args) {
        this.args = args;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", className='" + className + '\'' +
                ", rootPath='" + rootPath + '\'' +
                ", source=" + source +
                ", args=" + args +
                ", methodName='" + methodName + '\'' +
                '}';
    }
}
