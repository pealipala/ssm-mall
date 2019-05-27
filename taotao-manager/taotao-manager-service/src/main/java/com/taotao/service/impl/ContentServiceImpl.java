package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 *内容管理实现
 * @author : yechaoze
 * @date : 2019/5/5 12:14
 */
@Service
public class ContentServiceImpl implements ContentService{

    @Autowired
    private TbContentMapper tbContentMapper;
    
    /**
     * 获取内容列表
     * @author : yechaoze
     * @date : 2019/5/27 13:04
     * @param categoryId : 
     * @param page : 
     * @param rows : 
     * @return : com.taotao.common.pojo.EasyUIDataGridResult
     */
    @Override
    public EasyUIDataGridResult getContentList(Long categoryId, int page, int rows) {
        //根据categoryId查询
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //设置分页信息
        PageHelper.startPage(page,rows);
        List<TbContent> tbContents = tbContentMapper.selectByExample(example);
        EasyUIDataGridResult result=new EasyUIDataGridResult();
        result.setRows(tbContents);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        result.setTotal(pageInfo.getTotal());
        return result;
    }

    /**
     *新增内容
     * @author : yechaoze
     * @date : 2019/5/27 13:08
     * @param tbContent :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult addContent(TbContent tbContent) {
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        tbContentMapper.insert(tbContent);
        return TaoTaoResult.ok();
    }

    /**
     *更新内容
     * @author : yechaoze
     * @date : 2019/5/27 13:09
     * @param tbContent :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult upDateContent(TbContent tbContent) {
        tbContent.setUpdated(new Date());
        //仅对某一字段更新
        tbContentMapper.updateByPrimaryKeySelective(tbContent);
        return TaoTaoResult.ok();
    }

    /**
     *删除内容
     * @author : yechaoze
     * @date : 2019/5/27 13:09
     * @param id :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult deleteContent(List<Long> id) {
        for (int i=0;i<id.size();i++){
            tbContentMapper.deleteByPrimaryKey(id.get(i));
        }
        return TaoTaoResult.ok();
    }


}
