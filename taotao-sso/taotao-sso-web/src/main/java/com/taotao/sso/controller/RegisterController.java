package com.taotao.sso.controller;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.RegisterService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *注册功能
 * @author : yechaoze
 * @date : 2019/5/22 11:03
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    /**
     *显示注册页面
     * @author : yechaoze
     * @date : 2019/5/27 14:35
     * @return : java.lang.String
     */
    @RequestMapping("/page/register")
    public String showRegister(){
        return "register";
    }

    /**
     *校验数据
     * @author : yechaoze
     * @date : 2019/5/27 14:35
     * @param param :
     * @param type :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaoTaoResult checkData(@PathVariable String param,@PathVariable int type){
        return registerService.checkData(param,type);
    }

    /**
     *处理用户注册
     * @author : yechaoze
     * @date : 2019/5/27 14:36
     * @param user :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value="/user/register", method= RequestMethod.POST)
    @ResponseBody
    public TaoTaoResult register(TbUser user){
        return registerService.register(user);
    }
}
