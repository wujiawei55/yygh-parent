package com.lc.yygh.client.hosp;

import com.lc.yygh.vo.hosp.ScheduleOrderVo;
import com.lc.yygh.vo.order.SignInfoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName HospitalFeignClient
 * Description
 * Create by lujun
 * Date 2021/5/15 14:32
 */
@FeignClient("service-hosp")
public interface HospitalFeignClient {
    @GetMapping("/api/hosp/hospital/inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @PathVariable("scheduleId") String scheduleId);
    @GetMapping("/api/hosp/hospital/inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @PathVariable("hoscode") String hoscode);
}
