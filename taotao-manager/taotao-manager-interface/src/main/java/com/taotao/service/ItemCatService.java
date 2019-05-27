package com.taotao.service;

import com.taotao.common.pojo.EasyUITreeNode;

import java.util.List;
/**
 *商品类目：选择类目
 * @author : yechaoze
 * @date : 2019/5/5 12:10
 */
public interface ItemCatService {
    List<EasyUITreeNode> getItemCatList(long parentId);
}
