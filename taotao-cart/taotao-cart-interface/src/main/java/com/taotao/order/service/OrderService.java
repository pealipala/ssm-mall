package com.taotao.order.service;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.order.pojo.OrderInfo;

/**
 * 提交订单接口
 * @author : yechaoze
 * @date : 2019/5/27 14:39
 */
public interface OrderService {

    TaoTaoResult createOrder(OrderInfo orderInfo);//创建订单

}
