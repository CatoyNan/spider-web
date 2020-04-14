package top.catoy.service;

import top.catoy.entity.Script;

import java.util.List;

/**
 * @ClassName ScriptService
 * @Description TODO
 * @Author admin
 * @Date 2020-04-14 18:33
 * @Version 1.0
 **/
public interface ScriptService {
    Script getScriptById(int id);

    List<Script> getScriptByUserId(int id);

    void addScript(Script script);
}
