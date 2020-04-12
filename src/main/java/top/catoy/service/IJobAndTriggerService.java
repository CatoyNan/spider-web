package top.catoy.service;


import com.github.pagehelper.PageInfo;
import top.catoy.entity.JobAndTrigger;

public interface IJobAndTriggerService {
	public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
