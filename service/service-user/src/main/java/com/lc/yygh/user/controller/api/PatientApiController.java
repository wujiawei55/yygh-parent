package com.lc.yygh.user.controller.api;

import com.lc.yygh.common.result.Result;
import com.lc.yygh.common.utils.AuthContextHolder;
import com.lc.yygh.model.user.Patient;
import com.lc.yygh.user.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ClassName PatientApiController
 * Description 就诊人管理
 * Create by lujun
 * Date 2021/5/10 22:30
 */
@RestController
@RequestMapping("/api/user/patient")
public class PatientApiController {
    @Autowired
    private PatientService patientService;

    //获取就诊人列表
    @GetMapping("auth/findAll")
    public Result findAll(HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        //获取当前登录用户下的就诊人列表
        List<Patient> list = patientService.findByUserId(userId);
        return Result.success(list);
    }

    //添加就诊人
    @PostMapping("auth/save")
    public Result savePatient(@RequestBody Patient patient, HttpServletRequest request) {
        Long userId = AuthContextHolder.getUserId(request);
        patient.setUserId(userId);
        //调用Service中的save方法
        patientService.save(patient);
        return Result.success();
    }

    //根据id获取就诊人信息
    @GetMapping("auth/get/{id}")
    public Result getPatient(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return Result.success(patient);
    }
    //根据id获取就诊人信息(内部调用的)
    @GetMapping("inner/get/{id}")
    public Patient getPatientById(@PathVariable("id") Long id) {
         return patientService.getPatientById(id);
    }

    //修改就诊人
    @PutMapping("auth/update")
    public Result updatePatient(@RequestBody Patient patient) {
        patientService.updateById(patient);
        return Result.success();
    }

    //删除就诊人
    @DeleteMapping("auth/remove/{id}")
    public Result removePatient(@PathVariable Long id) {
        patientService.removeById(id);
        return Result.success();
    }

}