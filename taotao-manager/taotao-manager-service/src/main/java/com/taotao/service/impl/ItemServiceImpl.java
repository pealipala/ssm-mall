package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import java.util.Date;
import java.util.List;
/**
 *商品管理实现服务层
 * @author : yechaoze
 * @date : 2019/5/3 21:10
 */
@Service
public class ItemServiceImpl implements ItemService {

   @Autowired
   private TbItemMapper tbItemMapper;
   @Autowired
   private TbItemDescMapper tbitemDescMapper;
   @Autowired
   private JmsTemplate jmsTemplate;
   @Resource
   private Destination topicDestination;

   /**
     *显示商品及分页
     * @author : yechaoze
     * @date : 2019/5/2 23:45
     * @param page : 
     * @param rows : 
     * @return : com.taotao.common.pojo.EasyUIDataGridResult
     */
    @Override
    public EasyUIDataGridResult getItemList(int page, int rows) {
        //设置分页信息
        PageHelper.startPage(page, rows);
        //执行查询
        TbItemExample example=new TbItemExample();
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //创建一个返回值对象
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(list);
        //取分页结果
        PageInfo<TbItem> pageInfo=new PageInfo<>(list);
        //取总记录数
        long total = pageInfo.getTotal();
        result.setTotal(total);
        return result;
    }

    /**
     *新增商品
     * @author : yechaoze
     * @date : 2019/5/2 11:13
     * @param tbItem :
     * @param desc :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult addItem(TbItem tbItem, String desc) {
        //获取随机id值
        final long itemId = IDUtils.genItemId();
        //补全TbItem对象的属性
        tbItem.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        tbItem.setStatus((byte) 1);
        Date date=new Date();
        tbItem.setCreated(date);
        tbItem.setUpdated(date);
        //插入商品表
        tbItemMapper.insert(tbItem);
        // 创建一个TbItemDesc对象
        TbItemDesc itemDesc=new TbItemDesc();
        //补全TbItemDesc对象的属性
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(date);
        itemDesc.setUpdated(date);
        //插入商品描述表
        tbitemDescMapper.insert(itemDesc);
        //发送商品添加消息
        jmsTemplate.send(topicDestination, session -> session.createTextMessage(itemId+""));
        //补全
        return TaoTaoResult.ok();
    }

    /**
     *删除选中商品
     * @author : yechaoze
     * @date : 2019/5/2 15:56
     * @param itemId :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult deleteItem(List<Long> itemId) {
        TbItem tbItem=new TbItem();
        TbItemDesc desc=new TbItemDesc();
        for (int i=0;i<itemId.size();i++){
            tbItem.setId(itemId.get(i));
            desc.setItemId(itemId.get(i));
            tbItemMapper.deleteByPrimaryKey(tbItem.getId());
            tbitemDescMapper.deleteByPrimaryKey(desc.getItemId());
        }
        return TaoTaoResult.ok();
    }

    /**
     *返回商品信息和状态码
     * @author : yechaoze
     * @date : 2019/5/2 23:44
     * @param itemId : 
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult getItem(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return TaoTaoResult.ok(tbItem);
    }

    /**
     *返回商品描述信息和状态码
     * @author : yechaoze
     * @date : 2019/5/2 23:45
     * @param itemId : 
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult getItemDesc(Long itemId) {
        TbItemDesc desc = tbitemDescMapper.selectByPrimaryKey(itemId);
        return TaoTaoResult.ok(desc);
    }

    /**
     *更新商品及描述
     * @author : yechaoze
     * @date : 2019/5/2 23:52
     * @param tbItem :
     * @param desc :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult updateItem(TbItem tbItem, String desc) {
        Date date=new Date();
        tbItem.setUpdated(date);
        tbItemMapper.updateByPrimaryKeySelective(tbItem);
        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(tbItem.getId());
        tbItemDesc.setUpdated(date);
        tbItemDesc.setItemDesc(desc);
        tbitemDescMapper.updateByPrimaryKeySelective(tbItemDesc);
        return TaoTaoResult.ok();
    }

    @Override
    public TbItem getItemById(Long itemId) {
        TbItem tbItem = tbItemMapper.selectByPrimaryKey(itemId);
        return tbItem;
    }

    @Override
    public TbItemDesc getItemDescById(Long itemId) {
        TbItemDesc desc = tbitemDescMapper.selectByPrimaryKey(itemId);
        return desc;
    }


}
