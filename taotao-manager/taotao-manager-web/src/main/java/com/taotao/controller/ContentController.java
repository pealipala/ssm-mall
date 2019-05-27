package com.taotao.controller;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容controller
 * @author : yechaoze
 * @date : 2019/5/27 14:17
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService service;

    /**
     * 获取内容列表
     * @author : yechaoze
     * @date : 2019/5/27 14:17
     * @param categoryId :
     * @param page :
     * @param rows :
     * @return : com.taotao.common.pojo.EasyUIDataGridResult
     */
    @RequestMapping("/content/query/list")
    @ResponseBody
    public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows){
        return service.getContentList(categoryId, page, rows);
    }

    /**
     * 新增内容
     * @author : yechaoze
     * @date : 2019/5/27 14:18
     * @param tbContent :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/content/save")
    @ResponseBody
    public TaoTaoResult addContent(TbContent tbContent){
        return service.addContent(tbContent);
    }

    /**
     * 编辑内容
     * @author : yechaoze
     * @date : 2019/5/27 14:18
     * @param tbContent :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/rest/content/edit")
    @ResponseBody
    public TaoTaoResult upDateContent(TbContent tbContent){
        return service.upDateContent(tbContent);
    }

    /**
     * 删除内容
     * @author : yechaoze
     * @date : 2019/5/27 14:18
     * @param id :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/content/delete")
    @ResponseBody
    public TaoTaoResult deleteContent(@RequestParam(name = "ids") List<Long> id){
        return service.deleteContent(id);
    }



}
