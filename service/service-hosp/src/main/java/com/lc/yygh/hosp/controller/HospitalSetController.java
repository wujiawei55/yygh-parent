package com.lc.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lc.yygh.common.result.Result;
import com.lc.yygh.common.utils.MD5;
import com.lc.yygh.hosp.service.HospitalSetService;
import com.lc.yygh.model.hosp.HospitalSet;
import com.lc.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * ClassName HospitalSetController
 * Description
 * Create by lujun
 * Date 2021/4/16 21:31
 */
@Api(tags = "医院设置管理接口")
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
//解决跨域
//@CrossOrigin
public class HospitalSetController {
    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("/findAll")
    public Result getAll() {
        List<HospitalSet> list = hospitalSetService.list();

        return Result.success(list);
    }

    @ApiOperation(value = "根据id删除医院设置(逻辑删除)")
    @DeleteMapping("/{id}")
    public Result deleteHospSet(@PathVariable("id") Long id) {
        boolean b = hospitalSetService.removeById(id);
        if (b) {
            //返回Result.success()
            return Result.success();
        }
        return Result.fail();
    }


    // 医院设置锁定和解锁
    @ApiOperation("医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id, @PathVariable("status") Integer status) {
        //根据Id查出对应的医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置status值，1表示可用，0表示不可用
        hospitalSet.setStatus(status);
        //修改
        hospitalSetService.updateById(hospitalSet);
        return Result.success();
    }


    @ApiOperation("根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@ApiParam(value = "医院设置的id") @PathVariable Long id) {
        //以下代码是制造异常的

//        try {
//            int  m=10/0;
//        } catch (Exception e) {
//             //手动抛出自定义的异常
//            throw new YyghException(ResultStatus.DATA_ERROR);
//        }

        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.success(hospitalSet);
    }

    @ApiOperation("带条件+分页查询医院设置")
    @PostMapping("findPageHospSet/{current}/{size}")
    public Result findPageHospSet(@ApiParam(name = "current", value = "当前页") @PathVariable long current,
                                  @ApiParam(value = "每页条数") @PathVariable long size,
                                  @RequestBody
                                          (required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, size);
        //构建条件
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if (!StringUtils.isEmpty(hosname)) {
            wrapper.like("hosname", hosname);  // hosname like %hosname%
        }
        if (!StringUtils.isEmpty(hoscode)) {
            wrapper.eq("hoscode", hoscode);  //hoscode字段名=hoscode变量值
        }
        //调用方法实现分页查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, wrapper);
        //返回结果
        return Result.success(pageHospitalSet);
    }

    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet) {
        //设置状态,1能使用，0 不能使用
        hospitalSet.setStatus(1);
        //签名秘钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis() + "" + random.nextInt(1000)));
        //调用Service完成添加
        hospitalSetService.save(hospitalSet);
        return Result.success();
    }

    @ApiOperation(value = "修改医院设置")
    @PutMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.success();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "发送签名key")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信（未完成的事项）
        return Result.success();
    }
}
