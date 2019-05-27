package com.taotao.items.message;

import com.taotao.items.service.ItemService;
import com.taotao.pojo.Item;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 *监听商品添加消息，生成静态页面
 * @author : yechaoze
 * @date : 2019/5/18 20:11
 */
public class HtmlGenListener implements MessageListener {

    @Autowired
    private ItemService itemService;
//    @Autowired
//    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Value("${HTML_GEN_PATH}")
    private String HTML_GEN_PATH;

    @Override
    public void onMessage(Message message) {
        try {
            //创建一个模板，参考jsp
            //从消息中取商品id
            TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            Long itemId = new Long(text);
            //等待事务提交
            Thread.sleep(5000);
            //根据商品id查询商品信息，商品基本信息和商品描述。
            TbItem tbItem = itemService.getItemById(itemId);
            Item item = new Item(tbItem);
            //取商品描述
            TbItemDesc itemDesc = itemService.getItemDescById(itemId);
            //创建一个数据集，把商品数据封装
            Map data = new HashMap<>();
            data.put("item", item);
            data.put("itemDesc", itemDesc);
            //加载模板对象
            Configuration configuration = new Configuration(Configuration.getVersion());
//            Configuration configuration = freeMarkerConfigurer.getConfiguration();
            configuration.setDirectoryForTemplateLoading(new File("F:/code/taotao/taotao-items/taotao-items-web/src/main/webapp/WEB-INF/ftl"));
            Template template = configuration.getTemplate("item.ftl");
            configuration.setDefaultEncoding("utf-8");
            //创建一个输出流，指定输出的目录及文件名。
//            Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
            Writer out = new FileWriter(new File(HTML_GEN_PATH + itemId + ".html"));
//            Writer out = new FileWriter(HTML_GEN_PATH + itemId + ".html");
            //生成静态页面。
            template.process(data, out);
            //关闭流
            out.close();

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
