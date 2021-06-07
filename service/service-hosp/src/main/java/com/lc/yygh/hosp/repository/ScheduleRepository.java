package com.lc.yygh.hosp.repository;

import com.lc.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

/**
 * ClassName ScheduleRepository
 * Description
 * Create by lujun
 * Date 2021/4/28 17:27
 */
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    //获取一个排班信息
     Schedule  findByHoscodeAndHosScheduleId(String hoscode,String hosScheduleId);
    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> findByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}
