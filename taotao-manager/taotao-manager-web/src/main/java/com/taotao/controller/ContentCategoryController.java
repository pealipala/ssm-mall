package com.taotao.controller;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 内容分类controller
 * @author : yechaoze
 * @date : 2019/5/27 13:14
 */
@Controller
public class ContentCategoryController {
    
    @Autowired
    private ContentCategoryService service;
    
    /**
     * 取内容分类列表
     * @author : yechaoze
     * @date : 2019/5/27 13:14
     * @param parentId : 
     * @return : java.util.List<com.taotao.common.pojo.EasyUITreeNode>
     */
    @RequestMapping("/content/category/list")
    @ResponseBody
    public List<EasyUITreeNode> getContentCatList(
            @RequestParam(name = "id",defaultValue = "0") Long parentId){
        List<EasyUITreeNode> list = service.getContentCatList(parentId);
        return list;
    }

    /**
     *新增内容分类节点
     * @author : yechaoze
     * @date : 2019/5/27 13:14
     * @param parentId : 
     * @param name : 
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/content/category/create")
    @ResponseBody
     public TaoTaoResult addContentCategory(Long parentId,String name){
        TaoTaoResult result = service.addContentCategory(parentId, name);
        return result;
     }

     /**
      *重命名分类节点
      * @author : yechaoze
      * @date : 2019/5/27 13:15
      * @param id : 
      * @param name : 
      * @return : com.taotao.common.utils.TaoTaoResult
      */
    @RequestMapping("/content/category/update")
    @ResponseBody
    public TaoTaoResult renameContentCategory(Long id, String name){
        TaoTaoResult result = service.renameContentCategory(id, name);
        return result;
    }

    /**
     * 删除分类节点
     * @author : yechaoze
     * @date : 2019/5/27 13:15
     * @param id : 
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/content/category/delete")
    @ResponseBody
    public TaoTaoResult deleteContentCategory(Long id){
        TaoTaoResult result = service.deleteContentCategory(id);
        return result;
    }

}
