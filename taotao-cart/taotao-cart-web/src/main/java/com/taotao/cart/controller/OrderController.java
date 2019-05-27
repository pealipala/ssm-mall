package com.taotao.cart.controller;

import com.taotao.cart.service.CartService;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.order.pojo.OrderInfo;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单controller
 * @author : yechaoze
 * @date : 2019/5/26 20:51
 */
@Controller
public class OrderController {

    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    /**
     * 结算购物车
     * @author : yechaoze
     * @date : 2019/5/26 20:51
     * @param request :
     * @return : java.lang.String
     */
    @RequestMapping("/order/order-cart")
    public String showOrderCart(HttpServletRequest request) {
        //取用户id
        TbUser user= (TbUser) request.getAttribute("user");
        //根据用户id取收货地址列表
        //使用静态数据。。。
        //取支付方式列表
        //静态数据
        //根据用户id取购物车列表
        List<TbItem> cartList = cartService.getCartList(user.getId());
        //把购物车列表传递给jsp
        request.setAttribute("cartList", cartList);
        //返回页面
        return "order-cart";
    }

    /**
     * 生成订单
     * @author : yechaoze
     * @date : 2019/5/27 14:40
     * @param orderInfo :
     * @param request :
     * @return : java.lang.String
     */
    @RequestMapping(value = "/order/create",method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo,HttpServletRequest request){
        //取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //把用户信息添加到orderInfo中
        orderInfo.setUserId(user.getId());
        orderInfo.setBuyerNick(user.getUsername());
        //调用服务生成订单
        TaoTaoResult result = orderService.createOrder(orderInfo);
        //生成成功 删除购物车
        if (result.getStatus()==200){
             //清空购物车
            cartService.clearCartList(user.getId());
        }
        //订单号传递给页面
        request.setAttribute("orderId",result.getData());
        request.setAttribute("payment",orderInfo.getPayment());
        //返回逻辑视图
        return "success";
    }

}
