package com.taotao.sso.service;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbUser;

/**
 * 用户注册服务接口
 * @author : yechaoze
 * @date : 2019/5/25 17:13
 */
public interface RegisterService {

    TaoTaoResult checkData(String param,int type);//检查注册数据
    TaoTaoResult register(TbUser user);//注册

}
