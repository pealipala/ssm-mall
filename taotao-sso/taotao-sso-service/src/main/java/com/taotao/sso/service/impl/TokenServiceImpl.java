package com.taotao.sso.service.impl;

import com.taotao.common.redis.JedisClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *根据token查询用户信息
 * @author : yechaoze
 * @date : 2019/5/23 10:33
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaoTaoResult getToken(String token) {
        //获取token
        String json = jedisClient.get("SESSION:" + token);
        //取不到
        if (StringUtils.isBlank(json)){
            return TaoTaoResult.build(201,"用户登录已过期,请重新登录");
        }
        //取到token 更新session有效期
        jedisClient.expire("SESSION:"+token,1800);
        //将token转成TbUser
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaoTaoResult.ok(tbUser);
    }
}
