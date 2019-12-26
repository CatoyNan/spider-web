package top.catoy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.enums.TaskResultStatusEnum;
import top.catoy.entity.Response;
import top.catoy.service.CompilationService;

import java.util.ArrayList;

/**
 * @ClassName CompilationTaskController
 * @Description TODO
 * @Author admin
 * @Date 2019-11-28 18:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/task")
public class CompilationTaskController {
    private static final Logger logger = LoggerFactory.getLogger(CompilationTaskController.class);
    @Autowired
    CompilationService compilationService;
    /**
     * 获取客户的所有脚本
     * @return
     */
    @GetMapping("/getAllCompilationTasksByUserId")
    public Response hello() {
        System.out.println("134");
        Task compilationTask = new Task();
        Task compilationTask2 = new Task();

        compilationTask.setMethodName("calculate");
        compilationTask2.setMethodName("calculate");

        ArrayList<Object> objects = new ArrayList<>();
        objects.add("hello");
        compilationTask.setArgs(objects);
        compilationTask2.setArgs(objects);

        StringBuffer stringBuffer = new StringBuffer("package com.catoy.topp;\n" +
                "import java.util.HashMap;\n" +
                "import java.util.Map;\n" +
                "import top.catoy.scriptExecution.util.ClassUtil;\n" +
                "public class Hello{\n" +
                " /**\n" +
                " * 主入口\n" +
                " **/\n" +
                "  public Result calculate(String data){\n" +
                "    Result result = new Result();\n" +
                "    result.data1 = \"hello\";\n" +
                "    result.data2 = \"world\";\n" +
                "    Map<String,String> map = new HashMap();\n" +
                "    ClassUtl.testMethod();\n" +
                "    map.put(\"1\",\"hello\");\n" +
                "    map.put(\"2\",\"world\");\n" +
                "    result.data3 = map;\n" +
                "    return result;\n" +
                "  }\n" +
                "  \n" +
                "  /**\n" +
                "  * 数据\n" +
                "  **/\n" +
                "  class Data{\n" +
                "    String url;\n" +
                "    String name;\n" +
                "  }\n" +
                "  \n" +
                "  /**\n" +
                "  * 输出格式\n" +
                "  **/\n" +
                "  class Result{\n" +
                "   public String data1;\n" +
                "   public String data2;\n" +
                "   public Map data3;\n" +
                "  }\n" +
                "}\n");
        compilationTask.setSource(stringBuffer);
        compilationTask2.setSource(stringBuffer);
        ArrayList<Task> compilationTasks = new ArrayList<>();
        compilationTask.setId(1001);
        compilationTask2.setId(2001);
        compilationTasks.add(compilationTask);
        compilationTasks.add(compilationTask2);

        compilationTask.setClassName("Hello.java");
        compilationTask2.setClassName("Hello.java");
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),compilationTasks);
    }

    @PostMapping("/execute")
    public Response execute (@RequestBody @Validated Task compilationTask) {
        logger.info("compilationTask={}",compilationTask);
        return compilationService.execute(compilationTask);
    }


}
