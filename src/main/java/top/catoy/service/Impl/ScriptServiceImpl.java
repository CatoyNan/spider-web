package top.catoy.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.catoy.dao.ScriptMapper;
import top.catoy.dao.UserScriptMapper;
import top.catoy.entity.Script;
import top.catoy.entity.UserScript;
import top.catoy.service.ScriptService;

import java.util.List;

/**
 * @ClassName ScriptServiceImpl
 * @Description TODO
 * @Author admin
 * @Date 2020-04-14 18:34
 * @Version 1.0
 **/
@Service
public class ScriptServiceImpl implements ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private UserScriptMapper userScriptMapper;

    @Override
    public Script getScriptById(int id) {
        return scriptMapper.getScriptById(id);
    }

    @Override
    public List<Script> getScriptByUserId(int id) {
        return scriptMapper.getScriptByUserId(id);
    }

    @Override
    public void addScript(Script script) {
        scriptMapper.addScript(script);
        UserScript userScript = new UserScript();
        userScript.setScriptId(script.getId());
        userScript.setUserId(1);
        userScriptMapper.addUserScript(userScript);
    }
}
