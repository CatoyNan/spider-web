package top.catoy.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.catoy.entity.Script;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.enums.TaskResultStatusEnum;
import top.catoy.entity.Response;
import top.catoy.service.CompilationService;
import top.catoy.service.ScriptService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    ScriptService scriptService;

    @PostMapping("/push")
    public Response push(@RequestBody @Validated Task compilationTask) {
        Script script = new Script();
        String className = compilationTask.getClassName();
        script.setClassName(className);
        className = className.replace(".java","");
        script.setMethodName(compilationTask.getMethodName());
        script.setMethodArgs(StringUtils.join(compilationTask.getArgs(),","));
        script.setScriptContent(compilationTask.getSource().toString());
        script.setScriptName(className.replace(".java","").substring(className.lastIndexOf(".") + 1));
        scriptService.addScript(script);
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),null);
    }
    /**
     * 获取客户的所有脚本
     * @return
     */
    @GetMapping("/getAllCompilationTasksByUserId")
    public Response getAllCompilationTasksByUserId(@RequestParam String id) {
        List<Script> scriptList = scriptService.getScriptByUserId(Integer.valueOf(id));
        ArrayList<Task> compilationTasks = new ArrayList<>();
        for (Script s:scriptList) {
            Task compilationTask = new Task();
            ArrayList<Object> objects = new ArrayList<>();
            compilationTask.setSource(new StringBuffer(s.getScriptContent()));
            compilationTask.setMethodName(s.getMethodName());
            compilationTask.setClassName(s.getScriptName());
            compilationTask.setArgs(Arrays.asList((Object) s.getMethodArgs().split(",")));
            compilationTask.setId(s.getId());
            compilationTasks.add(compilationTask);
        }
        return new Response(TaskResultStatusEnum.SUCCESS.getCode(), TaskResultStatusEnum.SUCCESS.getMessage(),compilationTasks);
    }

    @PostMapping("/execute")
    public Response execute (@RequestBody @Validated Task compilationTask) {
//        logger.info("compilationTask={}",compilationTask);
        return compilationService.execute(compilationTask);
    }


}
