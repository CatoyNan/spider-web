package top.catoy.compile.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import top.catoy.compile.classLoader.DiskClassLoader;
import top.catoy.exception.CompileException;
import top.catoy.compile.enums.CompileExceptionStatusEnum;

import javax.tools.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ClassUtil {
    private static final Log logger = LogFactory.getLog(ClassUtil.class);
    private static JavaCompiler compiler;

    static {
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * 获取java文件路径
     *
     * @param file
     * @return
     */
    private static String getFilePath(String file) {
        int last1 = file.lastIndexOf('/');
        int last2 = file.lastIndexOf('\\');
        return file.substring(0, last1 > last2 ? last1 : last2) + File.separatorChar;
    }

    /**
     * 编译java文件
     *
     * @param ops   编译参数
     * @param files 编译文件
     */
    private static void javac(List<String> ops, String... files) {
        StandardJavaFileManager manager = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            //创建诊断信息监听器
            DiagnosticCollector<JavaFileObject> diagnosticListeners = new DiagnosticCollector<>();
            manager = compiler.getStandardFileManager(diagnosticListeners, null, null);
            Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(files);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnosticListeners, ops, null, it);
            task.call();

            if (diagnosticListeners.getDiagnostics().size() > 0) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticListeners.getDiagnostics()) {
                    //可以在此处自定义编译诊(错误)断信息的输出格式
                    stringBuffer.append(diagnostic.toString() + "\n");
                }
                throw new CompileException(CompileExceptionStatusEnum.SOURCE_COMPILE_ERROR.getCode(), CompileExceptionStatusEnum.SOURCE_COMPILE_ERROR.getMessage() + "\n" + stringBuffer);
            }

        } catch (CompileException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e);
        } finally {
            if (manager != null) {
                try {
                    manager.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成java文件
     *
     * @param file   文件名
     * @param source java代码
     * @throws Exception
     */
    private static void writeJavaFile(String file, String source) throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("Write Java Source Code to:" + file);
        }
        BufferedWriter bw = null;
        try {
            File dir = new File(getFilePath(file));
            if (!dir.exists())
                dir.mkdirs();
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    /**
     * 加载类
     *
     * @param name     类名
     * @param loadPath 加载路径
     * @return
     */
    private static Class<?> load(String name, String loadPath) throws CompileException {
        Class<?> cls = null;
        ClassLoader classLoader = null;
        try {
            classLoader = new DiskClassLoader(loadPath);
            if (name.endsWith(".java")) {
                name = name.replace(".java", "");
            }
            cls = classLoader.loadClass(name);
            logger.debug("Load Class[" + name + "] by " + classLoader);
        } catch (ClassNotFoundException e) {
            logger.error("loadPath:" + loadPath);
            throw new CompileException(CompileExceptionStatusEnum.CLASS_CAN_NOT_LOAD.getMessage(), e, CompileExceptionStatusEnum.CLASS_CAN_NOT_LOAD.getCode());
        } catch (Exception e) {
            logger.error(e);
        }
        return cls;
    }

    /**
     * 编译代码并加载类
     *
     * @param filePath java代码存储根路径 eg: User/admin/Desktop/hello/
     * @param source   java代码
     * @param clsName  类名 eg: top.catoy.Hello.java
     * @param ops      编译参数
     * @return
     */
    public static Class<?> loadClass(String filePath, String source, String clsName, List<String> ops) throws CompileException {
        try {
            String var1 = clsName.substring(0, clsName.lastIndexOf("."));
            String packageName = var1.substring(0, var1.substring(0, clsName.lastIndexOf(".")).lastIndexOf("."));
            String fileName = clsName.substring(packageName.length() + 1);
            writeJavaFile(filePath + "/" + packageName.replace(".", "/") + "/" + fileName, source);
            javac(ops, filePath + "/" + packageName.replace(".", "/") + "/" + fileName);
            return load(clsName, filePath + "/" + packageName.replace(".", "/") + "/");
        } catch (CompileException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.toString(), e);
        }
        return null;
    }

    /**
     * 调用类方法
     *
     * @param cls        类
     * @param methodName 方法名
     * @param paramsCls  方法参数类型
     * @param params     方法参数
     * @return
     */
    public static Object invoke(Class<?> cls, String methodName, Class<?>[] paramsCls, Object[] params) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IllegalArgumentException {
        Object result = null;
        Method method = cls.getDeclaredMethod(methodName, paramsCls);
        Object obj = cls.newInstance();
        result = method.invoke(obj, params);
        return result;
    }

}