package com.taotao.cart.service.impl;

import com.taotao.cart.service.CartService;
import com.taotao.common.redis.JedisClient;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车处理服务
 * @author : yechaoze
 * @date : 2019/5/25 12:47
 * @return : null
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;
    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TaoTaoResult addCart(long userId, long itemId,int num) {
        //向redis中添加购物车
        //数据类型是Hash key:userId field:itemId value:商品信息(json)
        //判断商品是否存在
        Boolean isExit = jedisClient.hexists("CART:" + userId, itemId + "");
        //如果存在--数量相加
        if (isExit){
            String json = jedisClient.hget("CART:" + userId, itemId + "");
            TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
            if (item!=null){
                item.setNum(item.getNum() + num);
            }
            //写回redis
            jedisClient.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(item));
            return TaoTaoResult.ok();
        }
        //不存在--根据商品id取商品信息
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        //设置商品数量
        item.setNum(num);
        //取一张图片
        if (StringUtils.isNotBlank(item.getImage())){
            item.setImage(item.getImage().split(",")[0]);
        }
        // 添加到购物车列表
        jedisClient.hset("CART:" + userId, itemId + "",JsonUtils.objectToJson(item));
        //返回成功
        return TaoTaoResult.ok();
    }

    @Override
    public TaoTaoResult mergeCart(long userId, List<TbItem> itemList) {
        //遍历商品列表
        for (TbItem list:itemList) {
            //把列表添加到购物车
            //判断购物车中是否有商品
            //有 数量相加
            //没有 添加新的商品
            addCart(userId,list.getId(),list.getNum());
        }
        //返回成功
        return TaoTaoResult.ok();
    }

    @Override
    public List<TbItem> getCartList(long userId) {
        //根据用户id取商品列表
        List<String> jsonList = jedisClient.hvals("CART:" + userId);
        List<TbItem> items=new ArrayList<>();
        for (String cart : jsonList){
            TbItem item=JsonUtils.jsonToPojo(cart,TbItem.class);
            items.add(item);
        }
        return  items;
    }

    @Override
    public TaoTaoResult updateCartList(long userId, long itemId, int num) {
        //从redis中取商品
        String json = jedisClient.hget("CART:" + userId, itemId + "");
        TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
        //更新数量
        if (item!=null) {
            item.setNum(num);
        }
        //将商品写回redis
        json=JsonUtils.objectToJson(item);
        jedisClient.hset("CART:" + userId, itemId + "",json);
        return TaoTaoResult.ok();
    }

    @Override
    public TaoTaoResult deleteCartList(long userId, long itemId) {
        //删除购物车商品
        jedisClient.hdel("CART:" + userId, itemId + "");
        return TaoTaoResult.ok();
    }

    @Override
    public TaoTaoResult clearCartList(long userId) {
        //清空购物车
        jedisClient.del("CART:" + userId);
        return TaoTaoResult.ok();
    }
}
