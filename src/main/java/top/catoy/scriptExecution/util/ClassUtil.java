package top.catoy.scriptExecution.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.catoy.scriptExecution.classLoader.DiskClassLoader;
import top.catoy.scriptExecution.enums.TaskExceptionStatusEnum;
import top.catoy.scriptExecution.exception.ExecutionException;

import javax.tools.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ClassUtil {
    private static final Logger logger = LoggerFactory.getLogger(ClassUtil.class);
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
            manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(files);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, diagnosticListeners, ops, null, it);
            if (!task.call()) {
                for (Diagnostic<? extends JavaFileObject> diagnostic : diagnosticListeners.getDiagnostics()) {
                    //可以在此处自定义编译诊(错误)断信息的输出格式
                    stringBuffer.append(diagnostic.toString()).append("\n");
                }
                throw new ExecutionException(TaskExceptionStatusEnum.SOURCE_COMPILE_ERROR.getCode(), TaskExceptionStatusEnum.SOURCE_COMPILE_ERROR.getMessage() + "\n" + stringBuffer);
            }

        } catch (ExecutionException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
     */
    private static void writeJavaFile(String file, String source) {
        logger.debug("Write Java Source Code to:{}", file);
        logger.info("file:{}", file);
        BufferedWriter bw = null;
        try {
            File dir = new File(getFilePath(file));
            if (!dir.exists()) {
                logger.info("file not exits");
                logger.info("mkdir success:{}",dir.mkdirs());
            }
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
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
    private static Class<?> load(String name, String loadPath) throws ExecutionException {
        Class<?> cls = null;
        ClassLoader classLoader;
        try {
            classLoader = new DiskClassLoader(loadPath);
            if (name.endsWith(".java")) {
                name = name.replace(".java", "");
            }
            cls = classLoader.loadClass(name);
            logger.debug("Load Class[" + name + "] by " + classLoader);
        } catch (ClassNotFoundException e) {
            logger.error("loadPath:" + loadPath);
            throw new ExecutionException(TaskExceptionStatusEnum.CLASS_CAN_NOT_LOAD.getMessage(), e, TaskExceptionStatusEnum.CLASS_CAN_NOT_LOAD.getCode());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
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
    public static Class<?> loadClass(String filePath, String source, String clsName, List<String> ops) throws ExecutionException {
        try {
            String var1 = clsName.substring(0, clsName.lastIndexOf("."));
            String packageName = var1.substring(0, var1.substring(0, clsName.lastIndexOf(".")).lastIndexOf("."));
            String fileName = clsName.substring(packageName.length() + 1);
            writeJavaFile(filePath + "/" + packageName.replace(".", "/") + "/" + fileName, source);
            javac(ops, filePath + "/" + packageName.replace(".", "/") + "/" + fileName);
            return load(clsName, filePath + "/" + packageName.replace(".", "/") + "/");
        } catch (ExecutionException e) {
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 调用类方法
     *
     * @param cls        类
     * @param methodName 方法名
     * @param paramsCls  方法参数类型
     * @param params     方法参数
     * @param resulPrint 输出的内容
     * @return
     */
    public static Object invoke(Class<?> cls, String methodName, Class<?>[] paramsCls, Object[] params, StringBuffer resulPrint) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IllegalArgumentException {
        logger.info("methodName={},params={}",methodName,params);
        PrintStream out = System.out;
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            System.setOut(printStream);
            Object result;
            Method method = cls.getDeclaredMethod(methodName, paramsCls);
            Object obj = cls.newInstance();
            result = method.invoke(obj, params);
            resulPrint.append(new String(outputStream.toByteArray(), StandardCharsets.UTF_8));
            logger.info("resultPrint={}",resulPrint);
            return result;
        } catch (InvocationTargetException e) {
            throw new ExecutionException("source execute error:\n" + e.getCause() + "\n" + e.getCause().getStackTrace()[0],e,TaskExceptionStatusEnum.INVOKE_Target_Exception.getCode());
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | IllegalArgumentException e) {
            throw new IllegalArgumentException("source invoke error:\n" + e.getMessage(),e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        } finally {
            System.setOut(out);
        }
    }


    public static void testMethod(){
        System.out.println("hello world");
    }

//    /**
//     * Object[] 转换成制定类型数组
//     * @param targetType
//     * @param arrayObjects
//     * @param <T>
//     * @return
//     */
//    public static <T> T[] convertArray(Class<T> targetType, Object[] arrayObjects) {
//        if (targetType == null) {
//            return (T[]) arrayObjects;
//        }
//        if (arrayObjects == null) {
//            return null;
//        }
//        T[] targetArray = (T[]) Array.newInstance(targetType, arrayObjects.length);
//        try {
//            System.arraycopy(arrayObjects, 0, targetArray, 0, arrayObjects.length);
//        } catch (ArrayStoreException e) {
//            e.printStackTrace();
//        }
//        return targetArray;
//    }
}