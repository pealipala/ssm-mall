import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

import java.io.IOException;

public class SolrJTest {
    /**
     *solr添加文档
     * @author : yechaoze
     * @date : 2019/5/10 8:42
     * @return : void
     */
    @Test
    public void addDocument () throws Exception{
        //创建一个SolrServer对象
        SolrServer solrServer=new HttpSolrServer("http://192.168.233.133:8080/solr");
        //创建一个文档对象SolrInputDocument
        SolrInputDocument document = new SolrInputDocument();
        //向文档对象中添加域。文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义。
        document.addField("id", "doc01");
        document.addField("item_title", "测试商品01");
        document.addField("item_price", 1000);
        //把文档写入索引库
        solrServer.add(document);
        //提交
        solrServer.commit();
    }
    /**
     *solr删除文档
     * @author : yechaoze
     * @date : 2019/5/10 8:42
     * @return : void
     */
    @Test
    public void deleteDocument() throws IOException, SolrServerException {
        //创建一个SolrServer对象
        SolrServer solrServer=new HttpSolrServer("http://192.168.233.133:8080/solr");
        solrServer.deleteByQuery("id:doc01");
        solrServer.commit();
    }
    /**
     *查询
     * @author : yechaoze
     * @date : 2019/5/12 22:55
     * @return : void
     */
    @Test
    public void queryIndex() throws Exception {
        //创建一个SolrServer对象。
        SolrServer solrServer = new HttpSolrServer("http://192.168.233.133:8080/solr/collection1");
        //创建一个SolrQuery对象。
        SolrQuery query = new SolrQuery();
        //设置查询条件。
        //query.setQuery("*:*");
        query.set("q", "*:*");
        //执行查询，QueryResponse对象。
        QueryResponse queryResponse = solrServer.query(query);
        //取文档列表。取查询结果的总记录数
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        System.out.println("查询结果总记录数：" + solrDocumentList.getNumFound());
        //遍历文档列表，从取域的内容。
        for (SolrDocument solrDocument : solrDocumentList) {
            System.out.println(solrDocument.get("id"));
            System.out.println(solrDocument.get("item_title"));
            System.out.println(solrDocument.get("item_sell_point"));
            System.out.println(solrDocument.get("item_price"));
            System.out.println(solrDocument.get("item_image"));
            System.out.println(solrDocument.get("item_category_name"));
        }
    }

}
