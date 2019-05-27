package com.taotao.items.service;


import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

/**
 * 具体商品展示接口
 * @author : yechaoze
 * @date : 2019/5/27 14:24
 */
public interface ItemService {

    TbItem getItemById(Long itemId);//详情页获取商品信息
    TbItemDesc getItemDescById(Long itemId);//详情页获取商品描述信息


}
