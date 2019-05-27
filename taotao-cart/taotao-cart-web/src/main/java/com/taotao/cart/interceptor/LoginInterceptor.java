package com.taotao.cart.interceptor;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录处理拦截器
 * @author : yechaoze
 * @date : 2019/5/25 12:12
 * @return : null
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        //前处理,执行handler之前执行此方法
        //返回true，放行;返回false，拦截
        //1.从cookie中取token
        String token = CookieUtils.getCookieValue(httpServletRequest, "token");
        //2.如果没有token。未登录状态。直接放行
        if (StringUtils.isBlank(token)){
            return true;
        }
        //3.取到token，调用sso系统中服务，根据token取用户信息
        TaoTaoResult result = tokenService.getToken(token);
        //4.没有取到用户信息：登陆过期，直接放行
        if (result.getStatus()!=200){
            return true;
        }
        //5.取到用户信息。登录状态
        TbUser user = (TbUser) result.getData();
        //6.把用户信息放到request中。只要在controller中判断reques是否包含用户信息，放行
        httpServletRequest.setAttribute("user",user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView modelAndView) throws Exception {
        //handler执行之后，返回modeAndView之前
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        //完成处理，返回modelAndView之后
        //可以在此处理异常
    }
}
