package com.lc.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.yygh.common.exception.YyghException;
import com.lc.yygh.common.result.ResultStatus;
import com.lc.yygh.hosp.mapper.HospitalSetMapper;
import com.lc.yygh.hosp.service.HospitalSetService;
import com.lc.yygh.model.hosp.HospitalSet;
import com.lc.yygh.vo.order.SignInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ClassName HospitalSetServiceImpl
 * Description
 * Create by lujun
 * Date 2021/4/16 21:24
 */
@Service
@Transactional
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {

    @Override
    public String getSignKey(String hosCode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper();
        wrapper.select("sign_key", "status").eq("hoscode", hosCode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        if (null == hospitalSet) {
            throw new YyghException(ResultStatus.HOSPITAL_OPEN);
        }
        if (hospitalSet.getStatus().intValue() == 0) {
            throw new YyghException(ResultStatus.HOSPITAL_LOCK);
        }
        return hospitalSet.getSignKey();
    }

    @Override
    public SignInfoVo getSignInfoVo(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        if(null == hospitalSet) {
            throw new YyghException(ResultStatus.HOSPITAL_OPEN);
        }
        SignInfoVo signInfoVo = new SignInfoVo();
        signInfoVo.setApiUrl(hospitalSet.getApiUrl());
        signInfoVo.setSignKey(hospitalSet.getSignKey());
        return signInfoVo;
    }
}
