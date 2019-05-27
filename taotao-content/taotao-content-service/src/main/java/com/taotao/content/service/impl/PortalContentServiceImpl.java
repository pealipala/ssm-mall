package com.taotao.content.service.impl;


import com.taotao.content.service.PortalContentService;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 获取首页广告内容服务层
 * @author : yechaoze
 * @date : 2019/5/27 14:28
 */
@Service
public class PortalContentServiceImpl implements PortalContentService {
    @Autowired
    private TbContentMapper mapper;

    /**
     *获取广告内容
     * @author : yechaoze
     * @date : 2019/5/27 14:28
     * @param cid :
     * @return : java.util.List<com.taotao.pojo.TbContent>
     */
    @Override
    public List<TbContent> getContentListById(Long cid) {
        TbContentExample example=new TbContentExample();
        TbContentExample.Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(cid);
        List<TbContent> list = mapper.selectByExampleWithBLOBs(example);
        return list;
    }
}
