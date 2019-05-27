package com.taotao.retrieve.dao;

 import com.taotao.common.pojo.SearchItem;
import com.taotao.common.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 根据查询条件查询索引库
 * @author : yechaoze
 * @date : 2019/5/13 8:16
 */
@Component
public class SearchDao {
    @Autowired
    private SolrServer solrServer;

    public SearchResult search(SolrQuery query) throws SolrServerException {
        //根据query查询索引库
        QueryResponse queryResponse = solrServer.query(query);
        //取查询结果。
        SolrDocumentList results = queryResponse.getResults();
        //获取总结果
        long numFound = results.getNumFound();
        SearchResult searchResult=new SearchResult();
        searchResult.setRecordCount(numFound);
        //取商品列表，需要取高亮显示
        List<SearchItem> list=new ArrayList<>();
        Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
        //遍历查询结果
        for(SolrDocument solrDocument:results){
            SearchItem item=new SearchItem();
            item.setId((String) solrDocument.get("id"));
            item.setCategory_name((String) solrDocument.get("item_category_name"));
            item.setImage((String) solrDocument.get("item_image"));
            item.setPrice((long) solrDocument.get("item_price"));
            item.setSell_point((String) solrDocument.get("item_sell_point"));
            //h获取高亮显示
            List<String> strings = highlighting.get(solrDocument.get("id")).get(solrDocument.get("item_title"));
            String title = "";
            if (strings!=null&&strings.size()>0){
                title=strings.get(0);
            }else {
                title= (String) solrDocument.get("item_title");
            }
            item.setTitle(title);
            //添加商品到列表
            list.add(item);
        }
        searchResult.setItemList(list);
        //返回结果
        return searchResult;
    }
}
