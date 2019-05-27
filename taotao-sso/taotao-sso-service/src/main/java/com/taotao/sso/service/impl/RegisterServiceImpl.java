package com.taotao.sso.service.impl;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * 处理用户注册服务层
 * @author : yechaoze
 * @date : 2019/5/25 17:16
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;

    /**
     *校验数据
     * @author : yechaoze
     * @date : 2019/5/27 14:34
     * @param param :
     * @param type :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult checkData(String param, int type) {
        TbUserExample example=new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1.用户名 2.手机号 3.邮箱
        if (type==1){
            criteria.andUsernameEqualTo(param);
        }else if (type==2){
            criteria.andPhoneEqualTo(param);
        }else if (type==3){
            criteria.andEmailEqualTo(param);
        }
        //查询
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        //查询到存在的数据 返回false
        if (tbUsers!=null&&tbUsers.size()>0){
            return TaoTaoResult.ok(false);
        }
        //返回true
        return TaoTaoResult.ok(true);
    }

    /**
     * 注册
     * @author : yechaoze
     * @date : 2019/5/27 14:34
     * @param user :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult register(TbUser user) {
        //判断用户名或密码或手机号是否为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword()) ||
                StringUtils.isBlank(user.getPhone())){
            return TaoTaoResult.build(400,"请输入完整的信息");
        }
        //1 用户名 2 手机号
        TaoTaoResult result = checkData(user.getUsername(), 1);
        if (!(boolean)result.getData()){
            return TaoTaoResult.build(400,"此用户名被占用");
        }
        result=checkData(user.getPhone(),2);
        if (!(boolean)result.getData()){
            return TaoTaoResult.build(400,"此手机号被占用");
        }
        //数据库中无重复记录，开始添加数据
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //密码使用md5加密
        String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pwd);
        //插入新注册数据
        userMapper.insert(user);
        return TaoTaoResult.ok();
    }
}
