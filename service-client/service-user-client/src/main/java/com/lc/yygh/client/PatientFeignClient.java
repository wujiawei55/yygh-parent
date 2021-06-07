package com.lc.yygh.client;

import com.lc.yygh.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * ClassName PatientFeignClient
 * Description
 * Create by lujun
 * Date 2021/5/15 14:04
 */
@FeignClient("service-user")
public interface PatientFeignClient {
    //根据id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    public Patient getPatientById(@PathVariable("id") Long id);
}
