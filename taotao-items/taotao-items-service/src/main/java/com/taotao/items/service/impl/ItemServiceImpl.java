package com.taotao.items.service.impl;

import com.taotao.items.service.ItemService;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 具体商品展示服务
 * @author : yechaoze
 * @date : 2019/5/27 14:25
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;
    @Autowired
    private TbItemDescMapper tbitemDescMapper;

    /**
     * 获取商品信息
     * @author : yechaoze
     * @date : 2019/5/27 14:25
     * @param itemId :
     * @return : com.taotao.pojo.TbItem
     */
    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    /**
     *获取商品描述信息
     * @author : yechaoze
     * @date : 2019/5/27 14:25
     * @param itemId :
     * @return : com.taotao.pojo.TbItemDesc
     */
    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        TbItemDesc desc = tbitemDescMapper.selectByPrimaryKey(itemId);
        return desc;
    }

}
