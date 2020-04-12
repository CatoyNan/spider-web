package top.catoy.dao;


import org.apache.ibatis.annotations.Mapper;
import top.catoy.entity.JobAndTrigger;

import java.util.List;

public interface JobAndTriggerMapper {
	public List<JobAndTrigger> getJobAndTriggerDetails();
}
