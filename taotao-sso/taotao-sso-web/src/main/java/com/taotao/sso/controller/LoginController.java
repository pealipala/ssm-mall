package com.taotao.sso.controller;

import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录controller
 * @author : yechaoze
 * @date : 2019/5/23 9:05
 */
@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     *显示登录页面
     * @author : yechaoze
     * @date : 2019/5/27 14:35
     * @param request :
     * @param model :
     * @return : java.lang.String
     */
    @RequestMapping("/page/login")
    public String showLogin(HttpServletRequest request, Model model){
        String redirect = CookieUtils.getCookieValue(request, "redirect");
        model.addAttribute("redirect",redirect);
        return "login";
    }

    /**
     * 处理用户登录
     * @author : yechaoze
     * @date : 2019/5/27 14:35
     * @param username :
     * @param password :
     * @param request :
     * @param response :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    @ResponseBody
    public TaoTaoResult userLogin(String username, String password,
                                  HttpServletRequest request,HttpServletResponse response){
        TaoTaoResult result = loginService.userLogin(username, password);
        //判断是否登录成功
        if (result.getStatus()==200){
            String token = result.getData().toString();
            //将token写入cookie
            CookieUtils.setCookie(request,response,"token",token);
        }
        return result;
    }


}
