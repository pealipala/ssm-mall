package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品 crud controller
 * @author : yechaoze
 * @date : 2019/5/27 12:47
 */
@Controller
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * 后台商品展示
     * @author : yechaoze
     * @date : 2019/5/27 12:48
     * @param page :
     * @param rows :
     * @return : com.taotao.common.pojo.EasyUIDataGridResult
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIDataGridResult getItemList(Integer page, Integer rows){
        EasyUIDataGridResult result = itemService.getItemList(page, rows);
        return  result;
    }

    /**
     * 后台商品增加
     * @author : yechaoze
     * @date : 2019/5/27 12:49
     * @param item :
     * @param desc :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public TaoTaoResult addItem(TbItem item, String desc){
        TaoTaoResult result=itemService.addItem(item, desc);
        return result;
    }

    /**
     * 后台商品删除
     * @author : yechaoze
     * @date : 2019/5/27 12:49
     * @param itemId :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "/rest/item/delete")
    @ResponseBody
        public TaoTaoResult deleteItem(@RequestParam(value = "ids") List<Long> itemId){
        TaoTaoResult result = itemService.deleteItem(itemId);
        return result;
    }


    /**
     * 后台获取商品信息
     * @author : yechaoze
     * @date : 2019/5/27 12:49
     * @param id :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "/rest/item/param/item/query/{id}")
    @ResponseBody
    public  TaoTaoResult showItem(@PathVariable Long id){
        TaoTaoResult item = itemService.getItem(id);
        return item;
    }

    /**
     * 后台获取商品描述信息
     * @author : yechaoze
     * @date : 2019/5/27 12:52
     * @param id :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TaoTaoResult showItemDesc(@PathVariable Long id){
        TaoTaoResult desc = itemService.getItemDesc(id);
        return desc;
    }

    /**
     * 后台商品更新修改
     * @author : yechaoze
     * @date : 2019/5/27 12:52
     * @param tbItem :
     * @param desc :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping(value = "rest/item/update")
    @ResponseBody
    public TaoTaoResult upData(TbItem tbItem,String desc){
        TaoTaoResult result = itemService.updateItem(tbItem,desc);
        return result;
    }




}
