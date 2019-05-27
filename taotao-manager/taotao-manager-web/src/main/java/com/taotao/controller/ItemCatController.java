package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类
 * @author : yechaoze
 * @date : 2019/5/27 14:18
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 选择商品类目
     * @author : yechaoze
     * @date : 2019/5/27 14:18
     * @param parentId :
     * @return : java.util.List<com.taotao.common.pojo.EasyUITreeNode>
     */
    @RequestMapping("/item/cat/list")
    @ResponseBody
    public List<EasyUITreeNode> getItemCat(@RequestParam(value = "id",defaultValue = "0") long parentId){
        List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
        return list;
    }

}
