package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;


import java.util.List;

/**
 *商品管理接口
 * @author : yechaoze
 * @date : 2019/5/5 12:08
 */
public interface ItemService {

    EasyUIDataGridResult getItemList(int page,int rows);//商品列表查询及分页
    TaoTaoResult addItem(TbItem tbItem, String desc); //添加商品
    TaoTaoResult deleteItem(List<Long> itemId);//删除商品
    TaoTaoResult getItem(Long itemId);//编辑商品 1:获取商品 2:获取商品描述 3:更新商品
    TaoTaoResult getItemDesc(Long itemId);
    TaoTaoResult updateItem(TbItem tbItem,String desc);
    TbItem getItemById(Long itemId);
    TbItemDesc getItemDescById(Long itemId);

}
