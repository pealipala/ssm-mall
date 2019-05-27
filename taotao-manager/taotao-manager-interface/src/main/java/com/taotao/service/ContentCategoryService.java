package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;

import java.util.List;

/**
 *内容分类管理接口
 * @author : yechaoze
 * @date : 2019/5/5 12:09
 */
public interface ContentCategoryService {
    List<EasyUITreeNode> getContentCatList(Long parentId);//获取节点列表0
    TaoTaoResult addContentCategory(Long parentId,String name);//新增节点
    TaoTaoResult renameContentCategory(Long id,String name);//重命名节点
    TaoTaoResult deleteContentCategory(Long id);//删除节点
}
