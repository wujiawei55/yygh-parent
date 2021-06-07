package com.lc.yygh.hosp.controller.api;

import com.lc.yygh.common.exception.YyghException;
import com.lc.yygh.common.helper.HttpRequestHelper;
import com.lc.yygh.common.result.Result;
import com.lc.yygh.common.result.ResultStatus;
import com.lc.yygh.common.utils.MD5;
import com.lc.yygh.hosp.service.DepartmentService;
import com.lc.yygh.hosp.service.HospitalService;
import com.lc.yygh.hosp.service.HospitalSetService;
import com.lc.yygh.hosp.service.ScheduleService;
import com.lc.yygh.model.hosp.Department;
import com.lc.yygh.model.hosp.Hospital;
import com.lc.yygh.model.hosp.Schedule;
import com.lc.yygh.vo.hosp.DepartmentQueryVo;
import com.lc.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ClassName ApiController
 * Description
 * Create by lujun
 * Date 2021/4/28 14:46
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;
    @Autowired
    private HospitalSetService hospitalSetService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("saveHospital")
    @ApiOperation(value = "上传医院")
    public Result saveHosptal(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, Object> map = HttpRequestHelper.switchMap(parameterMap);
        //获取参数中的签名key,它是MD5加密的
        String hospSign = (String) map.get("sign");
        //根据医院编码查医院设置中的签名
        String hosCode = (String) map.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hosCode);
        //把查询到的签名加密
        String signKeyMD5 = MD5.encrypt(signKey);
        //比较两个签名是否一致
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultStatus.SIGN_ERROR);
        }
        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoDataString = (String) map.get("logoData");
        if (!StringUtils.isEmpty(logoDataString)) {
            String logoData = logoDataString.replaceAll(" ", "+");
            //覆盖原来的值
            map.put("logoData", logoData);
        }
        hospitalService.save(map);
        return Result.success();
    }

    @PostMapping("/hospital/show")
    @ApiOperation(value = "获取医院信息")
    public Result findHosptal(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        String hosCode = (String) map.get("hoscode");
//        if(StringUtils.isEmpty(hosCode)) {
//            throw new YyghException(ResultStatus.PARAM_ERROR);
//        }
        //获取参数中的签名key,它是MD5加密的
        String hospSign = (String) map.get("sign");

        String signKey = hospitalSetService.getSignKey(hosCode);
        //把查询到的签名加密
        String signKeyMD5 = MD5.encrypt(signKey);
        //比较两个签名是否一致
        if (!hospSign.equals(signKeyMD5)) {
            throw new YyghException(ResultStatus.SIGN_ERROR);
        }
        //调用Service中的方法
        Hospital hospital = hospitalService.getHospital(hosCode);
        return Result.success(hospital);
    }

    @PostMapping("/saveDepartment")
    @ApiOperation(value = "上传科室")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        String hosCode = (String) map.get("hoscode");
        //TODO: 验证签名Key
        departmentService.save(map);
        return Result.success();
    }

    // /department/list

    @PostMapping("/department/list")
    @ApiOperation(value = "获取科室分页列表")
    public Result getDepartments(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO: 验证签名Key
        //从Map值获取参数的值
        Object pageStr = map.get("page");
        int page = StringUtils.isEmpty(pageStr) ? 1 : Integer.parseInt(pageStr.toString());
        Object limitStr = map.get("limit");
        int limit = StringUtils.isEmpty(limitStr) ? 10 : Integer.parseInt(limitStr.toString());
        //获取医院编码
        String hoscode = (String) map.get("hoscode");
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        Page<Department> pageModel = departmentService.selectPage(page, limit, departmentQueryVo);
        //  departmentQueryVo.setDepcode(depcode);

        return Result.success(pageModel);
    }

    @PostMapping("/department/remove")
    @ApiOperation(value = "删除科室")
    public Result remove(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) map.get("hoscode");
        String depcode = (String) map.get("depcode");
        //TODO: 验证签名Key
        //调用departmentService的方法，先根据hoscode和depCode获取一个科室，再根据id删除科室
        departmentService.remove(hoscode, depcode);
        return Result.success();
    }

    @PostMapping("/saveSchedule")
    @ApiOperation(value = "上传排班")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO: 验证签名Key
        scheduleService.save(map);
        return Result.success();
    }

    @PostMapping("schedule/list")
    @ApiOperation(value = "获取排班分页列表")
    public Result getSchedules(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        //TODO: 验证签名Key
        //从Map值获取参数的值
        Object pageStr = map.get("page");
        int page = StringUtils.isEmpty(pageStr) ? 1 : Integer.parseInt(pageStr.toString());
        Object limitStr = map.get("limit");
        int limit = StringUtils.isEmpty(limitStr) ? 10 : Integer.parseInt(limitStr.toString());
        //获取医院编码
        String hoscode = (String) map.get("hoscode");
        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        Page<Schedule> pageModel = scheduleService.selectPage(page, limit, scheduleQueryVo);
        return Result.success(pageModel);
    }
    @PostMapping("schedule/remove")
    @ApiOperation(value = "删除排班")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> map = HttpRequestHelper.switchMap(request.getParameterMap());
        String hoscode = (String) map.get("hoscode");
        String hosScheduleId = (String) map.get("hosScheduleId");
        //TODO: 验证签名Key
        //调用ScheduleService的方法，先根据hoscode和hosScheduleId获取一个排班，再根据id删除排班
        scheduleService.remove(hoscode, hosScheduleId);
        return Result.success();
    }

}
