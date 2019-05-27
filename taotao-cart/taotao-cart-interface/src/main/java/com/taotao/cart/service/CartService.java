package com.taotao.cart.service;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbItem;

import java.util.List;

/**
 * 购物车服务接口
 * @author : yechaoze
 * @date : 2019/5/25 17:28
 */
public interface CartService {

    TaoTaoResult addCart(long userId,long itemId,int num);//向redis添加购物车
    TaoTaoResult mergeCart(long userId, List<TbItem> itemList);//合并redis与cookie购物车
    List<TbItem> getCartList(long userId);//获取购物车列表
    TaoTaoResult updateCartList(long userId,long itemId,int num);//更新redis购物车
    TaoTaoResult deleteCartList(long userId,long itemId);//删除redis购物车商品
    TaoTaoResult clearCartList(long userId);//清空购物车

}
