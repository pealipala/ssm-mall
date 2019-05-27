package com.taotao.cart.interceptor;

import com.taotao.cart.service.CartService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 订单拦截器
 * @author : yechaoze
 * @date : 2019/5/26 15:57
 * @return : null
 */
public class OrderInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;
    @Autowired
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        //判断token是否存在
            //不存在则跳转登录页面 ，登录就跳转到请求的url
        if (StringUtils.isBlank(token)){
            CookieUtils.setCookie(httpServletRequest,httpServletResponse,"redirect","http://localhost:8089"+httpServletRequest.getRequestURI(),2);
            httpServletResponse.sendRedirect("http://localhost:8088/page/login?redirect =http://localhost:8089"+httpServletRequest.getRequestURI());
            return false;
        }
        //token存在，调用sso中token服务
        TaoTaoResult result = tokenService.getToken(token);
        //当前没有用户信息 重新登录
        if (result.getStatus()!=200){
            CookieUtils.setCookie(httpServletRequest,httpServletResponse,"redirect","http://localhost:8089"+httpServletRequest.getRequestURI(),2);
            httpServletResponse.sendRedirect("http://localhost:8088/page/login?redirect = http://localhost:8089"+httpServletRequest.getRequestURI());
            return false;
        }
        //取到用户信息，将用户信息写入request
        TbUser user = (TbUser) result.getData();
        httpServletRequest.setAttribute("user",user);
        //从cookie中取购物车
        String cart = CookieUtils.getCookieValue(httpServletRequest, "cart", true);
        //购物车不为空
        if (StringUtils.isNotBlank(cart)){
            //合并购物车
            cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cart, TbItem.class));
        }
        //放行
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
