package top.catoy.dao;

import top.catoy.entity.Script;

import java.util.List;

public interface ScriptMapper {
    Script getScriptById(int id);

    int addScript(Script script);

    List<Script> getScriptByUserId(int id);
}
