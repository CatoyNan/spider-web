package com.catoy;

import org.junit.Test;
import org.apache.log4j.Logger;
import top.catoy.compile.compileProcessor.DefaultCompileProcessor;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.util.ClassUtil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HelloTest
 * @Description TODO
 * @Author admin
 * @Date 2019-11-22 15:06
 * @Version 1.0
 **/
public class HelloTest {
    private static final Logger logger = Logger.getLogger(HelloTest.class);
    @Test
    public void compile() {
        StringBuilder sb = new StringBuilder();
        sb.append("package com.even.test;");
        sb.append("import org.apache.log4j.Logger;\n");
        sb.append("import java.util.Map;\nimport java.text.DecimalFormat;\n");
        sb.append("public class Sum{\n");
        sb.append("private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n");
        sb.append("public Double calculate(Map<String,Double> data){\n");
        sb.append("double d = (30*data.get(\"f1\") + 20*data.get(\"f2\") + 50*data.get(\"f3\"))/100;\n");
        sb.append("return Double.valueOf(df.format(d));}}\n");
        //设置编译参数
        ArrayList<String> ops = new ArrayList<String>();
        ops.add("-Xlint:unchecked");
        //编译代码，返回class
        Class<?> cls = ClassUtil.loadClass("/Users/admin/Desktop/class/com/even/test/Sum.java",sb.toString(),"com.even.test.Sum",ops);
        //准备测试数据
        Map<String,Double> data = new HashMap<String,Double>();
        data.put("f1", 10.0);
        data.put("f2", 20.0);
        data.put("f3", 30.0);
        //执行测试方法
        Object result = ClassUtil.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data});
        //输出结果
        logger.info(data);
        logger.info("(30*f1+20*f2+50*f3)/100 = "+result);
    }

    @Test
    public void test() {
        StringBuffer sb = new StringBuffer();
        sb.append("package catoy.top;\n" +
                "\n" +
                "import org.apache.log4j.Logger;\n" +
                "\n" +
                "import java.util.Map;\n" +
                "import java.text.DecimalFormat;\n" +
                "\n" +
                "public class Hello {\n" +
                "    private static final Logger logger = Logger.getLogger(Hello.class;\n" +
                "    private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n" +
                "\n" +
                "    public vid calculate(Map<String, Double> data) {\n" +
                "        int a = 1/0;\n" +
                "    }\n" +
                "}");

        Map<String,Double> data = new HashMap<String,Double>();
        data.put("f1", 10.0);
        data.put("f2", 20.0);
        data.put("f3", 30.0);

        CompilationTask compilationTask = new CompilationTask();
        compilationTask.setClassName("catoy.top.Hello.java");
//        compilationTask.setData(data);
        compilationTask.setRootPath("/Users/admin/Desktop/class");
        compilationTask.setSource(sb);

        DefaultCompileProcessor defaultCompileProcessor = new DefaultCompileProcessor();
        defaultCompileProcessor.setCompilationTask(compilationTask);
        Class<?> cls = defaultCompileProcessor.run();

        //执行测试方法
        Object result = ClassUtil.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data});
        //输出结果
        logger.info(data);
        logger.info("(30*f1+20*f2+50*f3)/100 = "+result);

    }

    @Test
    public void helloUtil() {
        Class<?> cls = CompilationTask.class;
        Method[] method = cls.getMethods();
        System.out.println();
//        Object obj = cls.newInstance();
//        result = method.invoke(obj, params);
    }

}
