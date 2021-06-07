package com.lc.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.yygh.model.user.Patient;

import java.util.List;

/**
 * ClassName PatientService
 * Description
 * Create by lujun
 * Date 2021/5/10 22:28
 */
public interface PatientService extends IService<Patient> {
    //获取当前用户下的就诊人列表
    List<Patient> findByUserId(Long userId);
    //根据id获取就诊人信息
    Patient getPatientById(Long id);
}
