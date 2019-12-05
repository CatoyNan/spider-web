package top.catoy.compile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.catoy.compile.Service.CompilationService;
import top.catoy.compile.entity.CompilationTask;
import top.catoy.compile.entity.CompileResponse;

/**
 * @ClassName CompilationTaskController
 * @Description TODO
 * @Author admin
 * @Date 2019-11-28 18:22
 * @Version 1.0
 **/
@RestController
@RequestMapping("/compilationtask")
public class CompilationTaskController {
    @Autowired
    CompilationService compilationService;
    /**
     * 获取客户的所有脚本
     * @return
     */
    @GetMapping("/getAllCompilationTasksByUserId")
    public String hello() {
        return "hello";
    }

    @PostMapping("/compile")
    public CompileResponse compile (@RequestBody CompilationTask compilationTask) {
        return compilationService.compile(compilationTask);
    }


}
