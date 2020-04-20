package top.catoy.scriptExecution.Processor;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.catoy.scriptExecution.entity.TaskResult;
import top.catoy.scriptExecution.entity.Task;
import top.catoy.scriptExecution.util.ClassUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ClassName DefaultInvokeProcessor
 * @Description TODO
 * @Author admin
 * @Date 2019-12-17 16:51
 * @Version 1.0
 **/
@Component
public class DefaultInvokeProcessor implements InvokeProsser{
    private static final Logger logger = LoggerFactory.getLogger(DefaultInvokeProcessor.class);
    @Override
    public TaskResult run(Task compilationTask) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException {
        //执行测试方法
        String methodName = compilationTask.getMethodName();
        Method[] ms = compilationTask.getClz().getMethods();
        Class<?> returnType = null;
        Class<?>[] paramTypes = null;
        StringBuffer resulPrint = new StringBuffer();
        Object[] params = compilationTask.getArgs().toArray();
        for (int i = 0; i < ms.length; i++) {
            if (ms[i].getName().equals(methodName)) {
                // 得到方法的返回值类型的类类型
                returnType = ms[i].getReturnType();
                System.out.print(returnType.getName() + " ");
                // 获取参数类类型数组
                paramTypes = ms[i].getParameterTypes();
                for (Class class2 : paramTypes) {
                    System.out.print(class2.getName() + ",");
                }
                System.out.println(")");
                break;
            }
        }
        Object resultData = ClassUtil.invoke(compilationTask.getClz(), methodName, paramTypes, params, resulPrint);
        String resultJsonData = JSON.toJSONString(resultData);
//        logger.info("resultJsonData:" + resultJsonData);
        return new TaskResult(resultData, resulPrint, true, true, null);
    }
}
