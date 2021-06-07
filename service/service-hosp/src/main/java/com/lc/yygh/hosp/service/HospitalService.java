package com.lc.yygh.hosp.service;

import com.lc.yygh.model.hosp.Hospital;
import com.lc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * ClassName HospitalService
 * Description
 * Create by lujun
 * Date 2021/4/28 14:21
 */
public interface HospitalService {
    Hospital getHospital(String hosCode);

    //保存医院
    void save(Map<String, Object> paramMap);

    //分页查询医院
    Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);

    //根据医院id查询医院的详情，用Map封装数据
    Map<String, Object> selectDetail(String id);

    //更新医院状态
    void updateStatus(String id, Integer status);
    //根据医院编码获取医院名称
    String getHospName(String hoscode);
     //根据医院名称关键字搜索医院列表
    List<Hospital> findByHosname(String hosname);
     //根据医院编码获取医院信息和预约规则信息
    Map<String,Object> item(String hoscode);

   // Hospital getByHoscode(String hoscode);
}
