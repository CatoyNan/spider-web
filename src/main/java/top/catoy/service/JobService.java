package top.catoy.service;

import com.github.pagehelper.PageInfo;
import top.catoy.entity.Task;
import top.catoy.entity.TaskDto;

/**
 * @ClassName JobService
 * @Description TODO
 * @Author admin
 * @Date 2020-04-16 03:25
 * @Version 1.0
 **/
public interface JobService {
    PageInfo<TaskDto> queryJob(int pageNum, int pageSize);

}
