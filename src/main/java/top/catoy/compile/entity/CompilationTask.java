package top.catoy.compile.entity;

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
    private Map<String,Object> data = new HashMap<>();

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

    public void setSource(StringBuffer source) {
        this.source = source;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
