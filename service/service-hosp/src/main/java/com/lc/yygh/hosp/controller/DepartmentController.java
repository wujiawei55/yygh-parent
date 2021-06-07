package com.lc.yygh.hosp.controller;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.hosp.service.DepartmentService;
import com.lc.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName DepartmentController
 * Description
 * Create by lujun
 * Date 2021/4/30 13:56
 */
@Api(tags = "科室管理")
@RestController
@RequestMapping("/admin/hosp/department")
//解决跨域
//@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //根据医院编号，查询医院所有科室列表
    @ApiOperation(value = "查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode) {
        List<DepartmentVo> list = departmentService.findDeptTree(hoscode);
        return Result.success(list);
    }
}
