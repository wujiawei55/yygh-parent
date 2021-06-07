package com.lc.yygh.hosp.service;

import com.lc.yygh.model.hosp.Schedule;
import com.lc.yygh.vo.hosp.ScheduleOrderVo;
import com.lc.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * ClassName ScheduleService
 * Description 排班接口
 * Create by lujun
 * Date 2021/4/28 17:28
 */
public interface ScheduleService {
    //上传排班
    void save(Map<String, Object> paramMap);
    //分页查询排班列表
    Page<Schedule> selectPage(int page, int limit, ScheduleQueryVo  scheduleQueryVo);
    //删除排班
    void remove(String hoscode, String hosScheduleId);
     //根据医院编码和科室编码分页查询排班规则数据
    Map<String, Object> getRuleSchedule(long page, long limit, String hoscode, String depcode);
    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
    //获取可预约排班数据
    Map<String, Object> getBookingScheduleRule(Integer page, Integer limit, String hoscode, String depcode);
    //根据id获取排班信息
    Schedule getById(String scheduleId);
    //根据排班id获取排班预约下单数据
     ScheduleOrderVo getScheduleOrderVo(String scheduleId);
    /**
     * 修改排班
     */
    void update(Schedule schedule);
}
