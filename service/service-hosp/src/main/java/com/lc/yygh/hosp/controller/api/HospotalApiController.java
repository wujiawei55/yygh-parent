package com.lc.yygh.hosp.controller.api;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.hosp.service.DepartmentService;
import com.lc.yygh.hosp.service.HospitalService;
import com.lc.yygh.hosp.service.HospitalSetService;
import com.lc.yygh.hosp.service.ScheduleService;
import com.lc.yygh.model.hosp.Hospital;
import com.lc.yygh.model.hosp.Schedule;
import com.lc.yygh.vo.hosp.DepartmentVo;
import com.lc.yygh.vo.hosp.HospitalQueryVo;
import com.lc.yygh.vo.hosp.ScheduleOrderVo;
import com.lc.yygh.vo.order.SignInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "网站的医院管理接口")
@RequestMapping("/api/hosp/hospital")
public class HospotalApiController {
    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit,
            @ApiParam(name = "hospitalQueryVo", value = "查询对象", required = false)
                    HospitalQueryVo hospitalQueryVo) {

        return Result.success(hospitalService.selectPage(page, limit, hospitalQueryVo));
    }


    @ApiOperation(value = "根据医院名称关键字搜索医院列表")
    @GetMapping("findByHosname/{hosname}")
    public Result findByHosname(
            @ApiParam(name = "hosname", value = "医院名称", required = true)
            @PathVariable String hosname) {
        List<Hospital> hospitals = hospitalService.findByHosname(hosname);
        return Result.success(hospitals);
    }

    //根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.success(list);
    }

    @ApiOperation(value = "查询医院信息及预约规则信息")
    @GetMapping("{hoscode}")
    public Result getItem(@ApiParam(name = "hoscode", value = "医院code", required = true)
                          @PathVariable String hoscode) {
        Map<String, Object> map = hospitalService.item(hoscode);
        return Result.success(map);
    }

    @Autowired
    private ScheduleService scheduleService;
    @ApiOperation(value = "获取可预约排班数据")
    @GetMapping("auth/getBookingScheduleRule/{page}/{limit}/{hoscode}/{depcode}")
    public Result getBookingSchedule(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Integer limit,
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode) {
        Map<String, Object> bookingScheduleRule =  scheduleService.getBookingScheduleRule(page, limit, hoscode, depcode);
        return Result.success(bookingScheduleRule);
    }
    @ApiOperation(value = "获取指定日期的排班数据")
    @GetMapping("auth/findScheduleList/{hoscode}/{depcode}/{workDate}")
    public Result findScheduleList(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable String hoscode,
            @ApiParam(name = "depcode", value = "科室code", required = true)
            @PathVariable String depcode,
            @ApiParam(name = "workDate", value = "排班日期", required = true)
            @PathVariable String workDate) {
        List<Schedule> list = scheduleService.getDetailSchedule(hoscode,depcode,workDate);
        return Result.success(list);
    }

    @ApiOperation(value = "根据排班id获取排班数据")
    @GetMapping("getSchedule/{scheduleId}")
    public Result getSchedule(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable String scheduleId) {
        Schedule schedule = scheduleService.getById(scheduleId);
        return Result.success(schedule);
    }

    @ApiOperation(value = "根据排班id获取预约下单数据")
    @GetMapping("inner/getScheduleOrderVo/{scheduleId}")
    public ScheduleOrderVo getScheduleOrderVo(
            @ApiParam(name = "scheduleId", value = "排班id", required = true)
            @PathVariable("scheduleId") String scheduleId) {
        return scheduleService.getScheduleOrderVo(scheduleId);
    }

    @ApiOperation(value = "获取医院签名信息")
    @GetMapping("inner/getSignInfoVo/{hoscode}")
    public SignInfoVo getSignInfoVo(
            @ApiParam(name = "hoscode", value = "医院code", required = true)
            @PathVariable("hoscode") String hoscode) {
        return hospitalSetService.getSignInfoVo(hoscode);
    }

}
