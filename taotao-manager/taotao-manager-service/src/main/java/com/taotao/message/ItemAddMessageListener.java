package com.taotao.message;

import com.taotao.common.pojo.SearchItem;
import com.taotao.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *监听商品添加事件
 * @author : yechaoze
 * @date : 2019/5/15 9:19
 */
public class ItemAddMessageListener implements MessageListener {

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private SolrServer solrServer;

    @Override
    public void onMessage(Message message) {
        try{
            //消息中取商品id
            TextMessage textMessage= (TextMessage) message;
            String text = textMessage.getText();
            Long itemId= Long.valueOf(text);
            //等待事务提交
            Thread.sleep(1000);
            //根据id查询商品
            SearchItem searchItem = itemMapper.getItemById(itemId);
            //创建一个文档对象
            SolrInputDocument document=new SolrInputDocument();
            //向文档对象添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            //把文档对象写入索引库
            solrServer.add(document);
            //提交
            solrServer.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
