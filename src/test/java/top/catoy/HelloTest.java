//package top.catoy;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import top.catoy.scriptExecution.Processor.DefaultCompileProcessor;
//import top.catoy.scriptExecution.entity.Task;
//import top.catoy.scriptExecution.util.ClassUtil;
//import top.catoy.service.CompilationService;
//
//import javax.annotation.Resource;
//import javax.tools.*;
//import java.io.*;
//import java.lang.reflect.InvocationTargetException;
//import java.net.InetAddress;
//import java.net.Socket;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @ClassName HelloTest
// * @Description TODO
// * @Author admin
// * @Date 2019-11-22 15:06
// * @Version 1.0
// **/
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class HelloTest {
//    @Resource
//    CompilationService compilationService;
//    private static final Logger logger = LoggerFactory.getLogger(HelloTest.class);
//    @Test
//    public void compile() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
//        StringBuilder sb = new StringBuilder();
//        sb.append("package com.even.test;");
//        sb.append("import org.apache.log4j.Logger;\n");
//        sb.append("import java.util.Map;\nimport java.text.DecimalFormat;\n");
//        sb.append("public class Sum{\n");
//        sb.append("private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n");
//        sb.append("public Double calculate(Map<String,Double> data){\n");
//        sb.append("double d = (30*data.get(\"f1\") + 20*data.get(\"f2\") + 50*data.get(\"f3\"))/100;\n");
//        sb.append("return Double.valueOf(df.format(d));}}\n");
//        //设置编译参数
//        ArrayList<String> ops = new ArrayList<String>();
//        ops.add("-Xlint:unchecked");
//        //编译代码，返回class
//        Class<?> cls = ClassUtil.loadClass("/Users/admin/Desktop/class/com/even/test/Sum.java",sb.toString(),"com.even.test.Sum",ops);
//        //准备测试数据
//        Map<String,Double> data = new HashMap<String,Double>();
//        data.put("f1", 10.0);
//        data.put("f2", 20.0);
//        data.put("f3", 30.0);
//        //执行测试方法
////        Object result = ClassUtil.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data},new StringBuffer());
//        //输出结果
////        logger.info(data);
////        logger.info("(30*f1+20*f2+50*f3)/100 = "+result);
//    }
//
//    @Test
//    public void test() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, UnsupportedEncodingException {
//        StringBuffer sb = new StringBuffer();
//        sb.append("package catoy.top;\n" +
//                "\n" +
//                "import org.apache.log4j.Logger;\n" +
//                "\n" +
//                "import java.util.Map;\n" +
//                "import java.text.DecimalFormat;\n" +
//                "\n" +
//                "public class Hello {\n" +
//                "    private static final Logger logger = Logger.getLogger(Hello.class);\n" +
//                "    private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n" +
//                "\n" +
//                "    public vid calculate(Map<String, Double> data) {\n" +
//                "       // int a = 1/0;\n" +
//                "    }\n" +
//                "}");
//        System.out.println(sb);
//        Map<String,Double> data = new HashMap<String,Double>();
//        data.put("f1", 10.0);
//        data.put("f2", 20.0);
//        data.put("f3", 30.0);
//
//        Task compilationTask = new Task();
//        compilationTask.setClassName("catoy.top.Hello.java");
////        compilationTask.setData(data);
//        compilationTask.setRootPath("/Users/admin/Desktop/class");
//        compilationTask.setSource(sb);
//
//        DefaultCompileProcessor defaultCompileProcessor = new DefaultCompileProcessor();
//        defaultCompileProcessor.setCompilationTask(compilationTask);
//        Class<?> cls = defaultCompileProcessor.run();
//
//        //执行测试方法
////        Object result = ClassUtil.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data},new StringBuffer());
//        //输出结果
////        logger.info(data);
////        logger.info("(30*f1+20*f2+50*f3)/100 = "+result);
//
//    }
//
//    @Test
//    public void helloUtil() {
//        Task compilationTask = new Task();
//        compilationTask.setRootPath("");
//        ArrayList<Object> args = new ArrayList<>();
//        args.add("args1");
//        args.add("args2");
//        compilationTask.setArgs(args);
//        compilationTask.setSource(new StringBuffer("package com.catoy.top;\n" +
//                "public class Helloo{\n" +
//                " /**\n" +
//                " * 主入口\n" +
//                " **/\n" +
//                "  public Result calculate(String data,String data2){\n" +
////                "    System.out.println(data.url + data.name);\n" +
////                "    Result r = new Result();\n" +
////                "    r.result = data.url + data.name;\n" +
//                "    return null;\n" +
//                "  }\n" +
//                "  \n" +
//                "  /**\n" +
//                "  * 数据\n" +
//                "  **/\n" +
//                "  class Data{\n" +
//                "    String url;\n" +
//                "    String name;\n" +
//                "  }\n" +
//                "  \n" +
//                "  /**\n" +
//                "  * 输出格式\n" +
//                "  **/\n" +
//                "  class Result{\n" +
//                "    String result;\n" +
//                "  }\n" +
//                "}"));
//        compilationTask.setClassName("com.catoy.top.Helloo.java");
////        compilationService.scriptExecution(compilationTask);
//    }
//
//    @Test
//    public void compile2() throws URISyntaxException {
//        StringBuffer stringBuffer = new StringBuffer("package com.catoy.topp;\n" +
//                "import org.apache.log4j.Logger;\n" +
//                "public class Hello{\n" +
//                " /**\n" +
//                " * 主入口\n" +
//                " **/\n" +
//                "  public Result calculate(String data,String data2){\n" +
//                "    System.out.println(\"123\");\n" +
////                "    Result r = new Result();\n" +
////                "    r.result = data.url + data.name;\n" +
//                "    return null;\n" +
//                "  }\n" +
//                "  \n" +
//                "  /**\n" +
//                "  * 数据\n" +
//                "  **/\n" +
//                "  class Data{\n" +
//                "    String url;\n" +
//                "    String name;\n" +
//                "  }\n" +
//                "  \n" +
//                "  /**\n" +
//                "  * 输出格式\n" +
//                "  **/\n" +
//                "  class Result{\n" +
//                "    String result;\n" +
//                "  }\n" +
//                "}");
//        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
//        StandardJavaFileManager standardFileManager = javaCompiler.getStandardFileManager(null, null, null);
//        JavaFileManager jfm = new ForwardingJavaFileManager(standardFileManager) {
//            public JavaFileObject getJavaFileForOutput(JavaFileManager.Location location,
//                                                       String className,
//                                                       JavaFileObject.Kind kind,
//                                                       FileObject sibling) throws IOException {
//                if(kind == JavaFileObject.Kind.CLASS) {
//                    return new SimpleJavaFileObject(URI.create(className + ".class"), JavaFileObject.Kind.CLASS) {
//                        public OutputStream openOutputStream() {
//                            return new FilterOutputStream(new ByteArrayOutputStream()) {
//                                public void close() throws IOException{
//                                    out.close();
//                                    ByteArrayOutputStream bos = (ByteArrayOutputStream) out;
//                                    System.out.println( new String(bos.toByteArray(),"utf-8"));
////                                    bytes.put(className, bos.toByteArray());
//                                }
//                            };
//                        }
//                    };
//                }else{
//                    return super.getJavaFileForOutput(location, className, kind, sibling);
//                }
//            }
//        };
//        JavaFileObject javaFileObject = new MyJavaFileObject("Hello.java",stringBuffer.toString());
//        Iterable<? extends JavaFileObject> files = Arrays.asList(javaFileObject);
//        JavaCompiler.CompilationTask task = javaCompiler.getTask(null,jfm,null,null,null,files);
//
//        task.call();
//    }
//
//    class MyJavaFileObject extends SimpleJavaFileObject {
//        private String contents = null;
//        public MyJavaFileObject(String className, String contents) throws URISyntaxException {
//            super(new URI(className), Kind.SOURCE);
//            this.contents = contents;
//        }
//
//        @Override
//        public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
//            return this.contents;
//        }
//    }
//
//    @Test
//    public void test4() throws Exception {
//        test5();
//    }
//
//    public void test5() throws Exception {
//        InetAddress inetAddress = InetAddress.getByName("www.baidu.com");
//        Socket socket = new Socket(inetAddress.getHostAddress(), 80);
//
//        if (socket.isConnected()) {
//            System.out.println("连接建立,远程地址:" + socket.getRemoteSocketAddress());
//        }
//
//        // 关键！此处在Socket的输出流写入HTTP的GET报文，请服务器做出响应。
//        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//        bw.write("GET / HTTP/1.1\r\n");
//        bw.write("Host: www.baidu.com\r\n");
//        bw.write("\r\n");
//        bw.flush();
//
//        // 开始读取远程服务器的响应数据。
//        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
//
//        byte[] buffer = new byte[1024];
//        int count = 0;
//        while (true) {
//            count = bis.read(buffer);
//            if (count == -1) {
//                break;
//            }
//
//            System.out.println(new String(buffer, 0, count, "UTF-8"));
//        }
//
//        bw.close();
//        bis.close();
//        socket.close();
//    }
//
//}
