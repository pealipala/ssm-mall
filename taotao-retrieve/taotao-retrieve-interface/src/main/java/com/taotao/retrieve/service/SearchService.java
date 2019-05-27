package com.taotao.retrieve.service;

import com.taotao.common.pojo.SearchResult;

/**
 * 关键字搜索商品接口
 * @author : yechaoze
 * @date : 2019/5/27 14:30
 */
public interface SearchService {

    SearchResult search(String keyword,int page,int rows) throws Exception;//搜索商品
}
