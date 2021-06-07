package com.lc.yygh.hosp.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.yygh.model.hosp.HospitalSet;
import com.lc.yygh.vo.order.SignInfoVo;

/**
 * ClassName HospitalSetService
 * Description  医院设置业务接口
 * Create by lujun
 * Date 2021/4/16 21:22
 */
public interface HospitalSetService  extends IService<HospitalSet> {
   // 获取签名key
    String  getSignKey(String hosCode);

    //获取签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
