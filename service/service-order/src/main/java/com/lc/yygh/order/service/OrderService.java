package com.lc.yygh.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lc.yygh.model.order.OrderInfo;
import com.lc.yygh.vo.order.OrderCountQueryVo;

import java.util.Map;

/**
 * ClassName OrderInfoService
 * Description
 * Create by lujun
 * Date 2021/5/15 13:55
 */
public interface OrderService extends IService<OrderInfo> {
     //生成订单，返回订单编号
      Long  saveOrder(String scheduleId, Long patientId);
    /**
     * 获取订单详情
     */
    OrderInfo getOrderInfo(Long id);
    /**
     * 就诊提醒
     */
    void patientTips();

    //订单统计,返回Map,里面保存2个数组
    Map<String,Object>  getCountMap(OrderCountQueryVo orderCountQueryVo);
}
