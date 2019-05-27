package com.taotao.retrieve.service.impl;

import com.taotao.common.pojo.SearchResult;
import com.taotao.retrieve.dao.SearchDao;
import com.taotao.retrieve.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 索引库搜索商品服务层
 * @author : yechaoze
 * @date : 2019/5/27 14:30
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchDao searchDao;

    /**
     * 关键字搜索商品并分页
     * @author : yechaoze
     * @date : 2019/5/27 14:31
     * @param keyword :
     * @param page :
     * @param rows :
     * @return : com.taotao.common.pojo.SearchResult
     */
    @Override
    public SearchResult search (String keyword, int page, int rows) throws Exception{
        //创建SolrQuery对象
        SolrQuery query=new SolrQuery();
        //设置查询条件
        query.setQuery(keyword);
        //设置分页
        if (page<=0) page=1;
        query.setStart((page-1)*rows);
        query.setRows(rows);
        //设置默认搜索域
        query.set("df","item_title");
        //开启高亮
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style=\"color:red\">");
        query.setHighlightSimplePost("</em>");
        //调用dao查询
        SearchResult searchResult = searchDao.search(query);
        //计算页数
        long total = searchResult.getRecordCount();
        int totalPage= (int) (total/rows);
        if (total%rows>0) totalPage++;
        //返回结果
        searchResult.setTotalPages(totalPage);
        return searchResult;
    }
}
