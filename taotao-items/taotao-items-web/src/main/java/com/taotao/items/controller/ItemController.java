package com.taotao.items.controller;

import com.taotao.items.service.ItemService;
import com.taotao.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 商品页controller
 * @author : yechaoze
 * @date : 2019/5/27 14:26
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 调用商品服务 展示商品所有信息
     * @author : yechaoze
     * @date : 2019/5/27 14:26
     * @param itemId :
     * @param model :
     * @return : java.lang.String
     */
    @RequestMapping("/item/{itemId}")
    public String showItemInfo(@PathVariable Long itemId, Model model) {
        //调用服务取商品基本信息
        TbItem tbItem = itemService.getItemById(itemId);
        Item item = new Item(tbItem);
        //取商品描述信息
        TbItemDesc itemDesc = itemService.getItemDescById(itemId);
        //把信息传递给页面
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", itemDesc);
        //返回逻辑视图
        return "item";
    }
}
