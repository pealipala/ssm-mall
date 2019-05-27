package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.pojo.TbContent;

import java.util.List;

/**
 *内容管理接口
 * @author : yechaoze
 * @date : 2019/5/5 12:11
 */
public interface ContentService {

    EasyUIDataGridResult getContentList(Long categoryId,int page,int rows);//分页
    TaoTaoResult addContent(TbContent tbContent); //新增
    TaoTaoResult upDateContent(TbContent tbContent);//修改
    TaoTaoResult deleteContent(List<Long> id) ;//删除

}
