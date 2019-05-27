package com.taotao.sso.controller;


import com.taotao.common.utils.TaoTaoResult;
import com.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 根据token查询用户信息
 * @author : yechaoze
 * @date : 2019/5/23 10:32
 */
@Controller
public class TokenController {
    @Autowired
    private TokenService tokenService;

//    @RequestMapping(value = "/user/token/{token}",
//            produces = MediaType.APPLICATION_JSON_UTF8_VALUE
//   /* "application/json;charset=utf-8"*/)
//    @ResponseBody
//    public String getUserToken(@PathVariable String token,String callback){
//        TaoTaoResult result = tokenService.tokenService(token);
//        //响应请求之前，判断是否为jsonp请求
//        if (!StringUtils.isBlank(callback)){
//            //把结果封装成js语句响应
//            return callback+"("+ JsonUtils.objectToJson(result)+");";
//        }
//        return JsonUtils.objectToJson(result);
//    }

    @RequestMapping(value = "/user/token/{token}")
    @ResponseBody
    public Object  getUserToken(@PathVariable String token,String callback){
        TaoTaoResult result = tokenService.getToken(token);
        //响应请求之前，判断是否为jsonp请求
        if (!StringUtils.isBlank(callback)){
            MappingJacksonValue value=new MappingJacksonValue(result);
            value.setJsonpFunction(callback);
            return value;
        }
        return result;
    }

}
