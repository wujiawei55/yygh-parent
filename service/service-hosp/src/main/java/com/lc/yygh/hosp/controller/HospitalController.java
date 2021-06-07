package com.lc.yygh.hosp.controller;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.hosp.service.HospitalService;
import com.lc.yygh.vo.hosp.HospitalQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * ClassName HospitalController
 * Description
 * Create by lujun
 * Date 2021/4/28 21:30
 */
@Api(tags = "医院管理接口")
@RestController
@RequestMapping("/admin/hosp/hospital")
//解决跨域
//@CrossOrigin
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;


    @ApiOperation(value = "获取分页列表")
    @PostMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true) @PathVariable Integer page,
            @ApiParam(name = "limit", value = "每页记录数", required = true) @PathVariable Integer limit,
            @ApiParam(name = "hospitalQueryVo", value = "查询对象", required = false)
            @RequestBody(required = false) HospitalQueryVo hospitalQueryVo) {

        return Result.success(hospitalService.selectPage(page, limit, hospitalQueryVo));
    }

    //    @GetMapping("/test1")
//    public Result  test1(){
//        String name = dictFiegnClient.getName("230404");
//        return Result.success(name);
//    }
    @ApiOperation(value = "获取医院详情")
    @GetMapping("/show/{id}")
    public Result show(@PathVariable("id") String id) {
        Map<String, Object> map = hospitalService.selectDetail(id);
        return Result.success(map);
    }

    //修改医院的上线状态
    @ApiOperation(value = "更新医院的上线状态")
    @PutMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable("id") String id, @PathVariable("status") Integer status) {

          //调用Service的方法完成状态的更新
        hospitalService.updateStatus(id,status);
        return Result.success();
    }

}
