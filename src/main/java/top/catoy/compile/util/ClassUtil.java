package top.catoy.compile.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.catoy.compile.classLoader.DiskClassLoader;

import javax.tools.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

public class ClassUtil {
    private static final Log logger = LogFactory.getLog(ClassUtil.class);
    private static JavaCompiler compiler;
    static{
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * 获取java文件路径
     * @param file
     * @return
     */
    private static String getFilePath(String file){
        int last1 = file.lastIndexOf('/');
        int last2 = file.lastIndexOf('\\');
        return file.substring(0, last1>last2?last1:last2)+File.separatorChar;
    }

    /**
     * 编译java文件
     * @param ops 编译参数
     * @param files 编译文件
     */
    private static StringBuffer javac(List<String> ops, String... files){
        StandardJavaFileManager manager = null;
        try{
            //创建诊断信息监听器, 用于手机诊断信息
            DiagnosticCollector<JavaFileObject> diagnosticListeners = new DiagnosticCollector<>();
            manager = compiler.getStandardFileManager(diagnosticListeners, null, null);
            Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(files);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnosticListeners, ops, null, it);
            task.call();
            if(logger.isDebugEnabled()){
                for(String file:files)
                    logger.debug("Compile Java File:" + file);
            }

            if (diagnosticListeners.getDiagnostics().size() > 0) {
                StringBuffer stringBuffer = new StringBuffer();
                for(Diagnostic<? extends JavaFileObject> diagnostic : diagnosticListeners.getDiagnostics()) {
                    //可以在此处自定义编译诊(错误)断信息的输出格式
                    stringBuffer.append("Error on line" + diagnostic.getLineNumber() + "\n");
                    stringBuffer.append("in" + diagnostic.getSource() + "\n");
                }
                return stringBuffer;
            }

        }catch(Exception e){
            logger.error("",e);
        }finally{
            if(manager!=null){
                try {
                    manager.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
    /**
     * 生成java文件
     * @param file 文件名
     * @param source java代码
     * @throws Exception
     */
    private static void writeJavaFile(String file ,String source)throws Exception{
        if(logger.isDebugEnabled()){
            logger.debug("Write Java Source Code to:"+file);
        }
        BufferedWriter bw = null;
        try{
            File dir = new File(getFilePath(file));
            if(!dir.exists())
                dir.mkdirs();
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.flush();
        }catch(Exception e){
            throw e;
        }finally{
            if(bw!=null){
                bw.close();
            }
        }
    }
    /**
     * 加载类
     * @param name 类名
     * @param loadPath 加载路径
     * @return
     */
    private static Class<?> load(String name,String loadPath){
        Class<?> cls = null;
        ClassLoader classLoader = null;
        try{
            classLoader = new DiskClassLoader(loadPath);
            if(name.endsWith(".java")) {
                name = name.replace(".java","");
            }
            cls = classLoader.loadClass(name);
            if(logger.isDebugEnabled()){
                logger.debug("Load Class["+name+"] by "+classLoader);
            }
        }catch(Exception e){
            logger.error(e);
        }
        return cls;
    }

    /**
     * 编译代码并加载类
     * @param filePath java代码存储根路径 eg: User/admin/Desktop/hello/
     * @param source java代码
     * @param clsName 类名 eg: top.catoy.Hello.java
     * @param ops 编译参数
     * @return
     */
    public static Class<?> loadClass(String filePath,String source,String clsName,List<String> ops){
        try {
            String var1 = clsName.substring(0,clsName.lastIndexOf("."));
            String packageName = var1.substring(0,var1.substring(0,clsName.lastIndexOf(".")).lastIndexOf("."));
            String fileName = clsName.substring(packageName.length() + 1);
            writeJavaFile(filePath + "/" + packageName.replace(".","/") + "/" +fileName,source);
            StringBuffer compileErrorMsg = javac(ops,filePath + "/" + packageName.replace(".","/") + "/" +fileName);
            return load(clsName,filePath + "/" + packageName.replace(".","/") + "/");
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * 调用类方法
     * @param cls 类
     * @param methodName 方法名
     * @param paramsCls 方法参数类型
     * @param params 方法参数
     * @return
     */
    public static Object invoke(Class<?> cls,String methodName,Class<?>[] paramsCls,Object[] params){
        Object result = null;
        try {
            Method method = cls.getDeclaredMethod(methodName, paramsCls);
            Object obj = cls.newInstance();
            result = method.invoke(obj, params);
        } catch (Exception e) {
            logger.error("invoke出错",e);
        }
        return result;
    }
}