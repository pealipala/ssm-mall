package com.taotao.content.service;

import com.taotao.pojo.TbContent;

import java.util.List;
/**
 * 获取首页广告内容接口
 * @author : yechaoze
 * @date : 2019/5/27 14:27
 */
public interface PortalContentService {

   List<TbContent> getContentListById(Long cid);//获取内容列表

}
