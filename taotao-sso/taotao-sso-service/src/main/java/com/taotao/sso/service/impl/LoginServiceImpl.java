package com.taotao.sso.service.impl;

import com.taotao.common.redis.JedisClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * 处理用户登录服务层
 * @author : yechaoze
 * @date : 2019/5/23 8:39
 */
@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Override
    public TaoTaoResult userLogin(String username, String password) {
        //数据中查询有无用户名
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(example);
        //未查到相应用户名
        if (users==null || users.size()==0){
            return TaoTaoResult.build(400,"用户名或密码错误");
        }
        //查询成功 获取查询到的数据
        TbUser user = users.get(0);
        //密码md5加密后进行比对
        String md5= DigestUtils.md5DigestAsHex(password.getBytes());
        //密码错误
        if (!md5.equals(user.getPassword())){
            return TaoTaoResult.build(400,"用户名或密码错误");
        }
        //密码相同 生成token
        String token = UUID.randomUUID().toString();
        //将用户信息保存到redis,key:token value:用户信息
        user.setPassword(null);
        jedisClient.set("SESSION:"+token, JsonUtils.objectToJson(user));
        //设置session有效期 30分钟
        jedisClient.expire("SESSION:"+token,1800);
        return TaoTaoResult.ok(token);
    }
}
