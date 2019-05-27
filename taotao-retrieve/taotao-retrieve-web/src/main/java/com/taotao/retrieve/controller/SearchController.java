package com.taotao.retrieve.controller;

import com.taotao.common.pojo.SearchResult;
import com.taotao.retrieve.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 商品搜索controller
 * @author : yechaoze
 * @date : 2019/5/27 14:32
 */
@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

//   @Value("${SEARCH_RESULT_ROWS}")
//    private  Integer SEARCH_RESULT_ROWS;

    @RequestMapping("/search")
    public String searchItemList(String keyword, @RequestParam(defaultValue = "1") Integer page, Model model) throws Exception {
        keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
        //查询商品结果
        SearchResult result = searchService.search(keyword, page,60);
        //结果传递页面
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",result.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recordCount",result.getRecordCount());
        model.addAttribute("itemList",result.getItemList());
        //返回逻辑视图
        return "search";
    }
}
