package com.taotao.sso.service;

import com.taotao.common.utils.TaoTaoResult;

/**
 * 获取token服务接口
 * @author : yechaoze
 * @date : 2019/5/25 17:13
 */
public interface TokenService {

    TaoTaoResult getToken(String token);//获取token

}
