package com.taotao.controller;

import com.taotao.common.utils.TaoTaoResult;
import com.taotao.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *  索引库controller
 * @author : yechaoze
 * @date : 2019/5/27 14:20
 */
@Controller
public class SearchItemController {

    @Autowired
    private SearchItemService searchItemService;

    /**
     * 导入索引库
     * @author : yechaoze
     * @date : 2019/5/27 14:20
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/index/item/import")
    @ResponseBody
    public TaoTaoResult importItemList(){
        return searchItemService.importAllItem();
    }
}
