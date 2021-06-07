package com.lc.yygh.msm.service;

import com.lc.yygh.vo.msm.MsmVo;

public interface MsmService {
    //发送手机验证码
    boolean sendMsm(String phone,String code);
    //发送短信
    boolean send(MsmVo msmVo);
}
