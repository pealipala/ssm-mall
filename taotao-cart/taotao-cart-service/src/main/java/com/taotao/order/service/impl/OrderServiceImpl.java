package com.taotao.order.service.impl;

import com.taotao.common.redis.JedisClient;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 订单处理操作服务层
 * @author : yechaoze
 * @date : 2019/5/26 20:56
 */
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;
    @Value("${ORDER_ID_START}")
    private String ORDER_ID_START;
    @Value("${ORDER_DETAIL_ID_GEN_KEY}")
    private String ORDER_DETAIL_ID_GEN_KEY;

    @Override
    public TaoTaoResult createOrder(OrderInfo orderInfo) {
        if (!jedisClient.exists(ORDER_ID_GEN_KEY)){
            jedisClient.set(ORDER_ID_GEN_KEY,ORDER_ID_START);
        }
        //生成订单号 使用redis incr生成
        String orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
        //补全OrderInfo属性
        orderInfo.setOrderId(orderId);
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        orderInfo.setStatus(1);
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderInfo);
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem list:orderItems) {
            //生成明细id
            String orderDetailId = jedisClient.incr(ORDER_DETAIL_ID_GEN_KEY).toString();
            //补全属性
            list.setId(orderDetailId);
            list.setOrderId(orderId);
            //订单明细表插入数据
            orderItemMapper.insert(list);
        }
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        //补全属性
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        //订单物流表插入数据
        orderShippingMapper.insert(orderShipping);
        //返回成功，包含订单号
        return TaoTaoResult.ok(orderId);
    }
}
