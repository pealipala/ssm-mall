package com.taotao.service.impl;

import com.taotao.common.pojo.SearchItem;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.ItemMapper;
import com.taotao.service.SearchItemService;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 索引库服务
 * @author : yechaoze
 * @date : 2019/5/27 13:12
 */
@Service
public class SearchItemServiceImpl implements SearchItemService{

    @Autowired
    private ItemMapper mapper;
    @Autowired
    private SolrServer solrServer;

    /**
     * 导入索引库
     * @author : yechaoze
     * @date : 2019/5/27 13:12
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult importAllItem() {
        //获取商品列表
        List<SearchItem> itemList = mapper.getItemList();
        try {
            //遍历商品列表
            for (SearchItem searchItem : itemList) {
                //创建文档对象
                SolrInputDocument document = new SolrInputDocument();
                //向文档对象中添加域
                document.addField("id", searchItem.getId());
                document.addField("item_title", searchItem.getTitle());
                document.addField("item_sell_point", searchItem.getSell_point());
                document.addField("item_price", searchItem.getPrice());
                document.addField("item_image", searchItem.getImage());
                document.addField("item_category_name", searchItem.getCategory_name());
                //把文档对象写入索引库
                solrServer.add(document);
            }
            solrServer.commit();
            return TaoTaoResult.ok();
        }catch (Exception e){
            e.printStackTrace();
            return TaoTaoResult.build(500,"数据上传失败");
        }
    }
}
