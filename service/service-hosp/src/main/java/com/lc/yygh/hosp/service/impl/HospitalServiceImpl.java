package com.lc.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.lc.yygh.cmnclient.DictFeignClient;
import com.lc.yygh.enums.DictEnum;
import com.lc.yygh.hosp.repository.HpsipitalRepository;
import com.lc.yygh.hosp.service.HospitalService;
import com.lc.yygh.model.hosp.BookingRule;
import com.lc.yygh.model.hosp.Hospital;
import com.lc.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName HospitalServiceImpl
 * Description
 * Create by lujun
 * Date 2021/4/28 14:24
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HpsipitalRepository hospitalRepository;
    @Autowired
    private DictFeignClient dictFiegnClient;

    @Override
    public void save(Map<String, Object> paramMap) {
        String s = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(s, Hospital.class);
        //根据医院编码在mongoDB库中查询是否存在对应的医院，如果不存在，就执行添加，否则就执行更新
        Hospital hospitalExit = hospitalRepository.findByHoscode(hospital.getHoscode());

        if (null == hospitalExit) {
            Date date = new Date();
            //0：未上线 1：已上线
            hospital.setStatus(0);
            hospital.setCreateTime(date);
            hospital.setUpdateTime(date);
            hospital.setIsDeleted(0);
            //新增文档到mongoDB库
            hospitalRepository.save(hospital);
        } else {
            hospitalExit.setUpdateTime(new Date());
            hospitalExit.setIsDeleted(0);
            //更新文档
            hospitalRepository.save(hospitalExit);
        }
    }

    @Override
    public Hospital getHospital(String hosCode) {
        Hospital hospital = hospitalRepository.findByHoscode(hosCode);
        return hospital;
    }

    @Override
    public Page<Hospital> selectPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        // 创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING) //改变默认字符串匹配方式：模糊查询
                .withIgnoreCase(true); //改变默认大小写忽略方式：忽略大小写
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo, hospital);
        Example<Hospital> example = Example.of(hospital, matcher);

        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);
        for (Hospital item : pages.getContent()) {
            this.packHospital(item);
        }
        return pages;
    }


    //封装hospital对象
    private void packHospital(Hospital hospital) {
        //获取医院等级名称
        String hostypeName = dictFiegnClient.getName(DictEnum.HOSTYPE.getDictCode(), hospital.getHostype());
        //获取省，市，区名称
        String provinceName = dictFiegnClient.getName(hospital.getProvinceCode());
        String cityName = dictFiegnClient.getName(hospital.getCityCode());
        String districtName = dictFiegnClient.getName(hospital.getDistrictCode());
        //用param来存储上面的4个数据
        hospital.getParam().put("hostypeName", hostypeName);
        // hospital.getParam().put("fullAddress", new String[]{provinceName ,cityName ,districtName});
        hospital.getParam().put("fullAddress", provinceName + cityName + districtName);

    }

    @Override
    public Map<String, Object> selectDetail(String id) {
        Map<String, Object> map = new HashMap<>();
        //从MongoDB库查询医院对象
        Hospital hospital = hospitalRepository.findById(id).get();
        this.packHospital(hospital);
        //存医院
        map.put("hospital", hospital);
        //单独处理预约规则
        map.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);
        return map;
    }

    @Override
    public void updateStatus(String id, Integer status) {
        //查询医院对象
        Hospital hospital = hospitalRepository.findById(id).get();
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        //保存
        hospitalRepository.save(hospital);

    }

    @Override
    public String getHospName(String hoscode) {
        String hosname = hospitalRepository.findByHoscode(hoscode).getHosname();
        return hosname;
    }

    @Override
    public List<Hospital> findByHosname(String hosname) {
        List<Hospital> list = hospitalRepository.findByHosnameLike(hosname);
        return list;
    }

    @Override
    public Map<String, Object> item(String hoscode) {
        Map<String, Object> map = new HashMap<>();
        //查询医院信息
        Hospital hospital = this.getHospital(hoscode);
        this.packHospital(hospital);

        //查询预约规则信息
        BookingRule bookingRule = hospital.getBookingRule();
        //存到Map中
        map.put("hospital", hospital);
        map.put("bookingRule", bookingRule);
        //不重复返回预约规则
        hospital.setBookingRule(null);
        return map;
    }


}
