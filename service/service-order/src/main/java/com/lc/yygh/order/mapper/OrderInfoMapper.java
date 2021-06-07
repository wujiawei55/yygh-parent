package com.lc.yygh.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lc.yygh.model.order.OrderInfo;
import com.lc.yygh.vo.order.OrderCountQueryVo;
import com.lc.yygh.vo.order.OrderCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ClassName OrderInfoMapper
 * Description
 * Create by lujun
 * Date 2021/5/15 13:55
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    List<OrderCountVo>  selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
