package com.taotao.sso.service;

import com.taotao.common.utils.TaoTaoResult;

/**
 * 用户 登录服务接口
 * @author : yechaoze
 * @date : 2019/5/25 17:13
 */
public interface LoginService {

    TaoTaoResult userLogin(String username,String password);//用户登录

}
