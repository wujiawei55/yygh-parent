package com.lc.yygh.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.yygh.cmnclient.DictFeignClient;
import com.lc.yygh.enums.DictEnum;
import com.lc.yygh.model.user.Patient;
import com.lc.yygh.user.mapper.PatientMapper;
import com.lc.yygh.user.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName PatientServiceImpl
 * Description
 * Create by lujun
 * Date 2021/5/10 22:29
 */
@Service
@Transactional
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    //    @Autowired
//    private PatientMapper patMapper;
    @Autowired
    private DictFeignClient dictFeignClient;

    @Override
    public List<Patient> findByUserId(Long userId) {
        QueryWrapper<Patient> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Patient> patients = baseMapper.selectList(wrapper);
        //调用方法完善就诊人信息，把编号转换为对应的字典名称
        patients.stream().forEach(item -> {
            //其他参数封装
            this.packPatient(item);
        });
        return patients;
    }

    private void packPatient(Patient patient) {
        //调用数据字典微服务中的方法，获取字典名称
        String certificatesTypeString = dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());
        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getContactsCertificatesType());
        //省
        String provinceString = dictFeignClient.getName(patient.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(patient.getCityCode());
        //区
        String districtString = dictFeignClient.getName(patient.getDistrictCode());
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());

    }

    @Override
    public Patient getPatientById(Long id) {
        Patient patient = baseMapper.selectById(id);
        this.packPatient(patient);
        return patient;
    }
}
