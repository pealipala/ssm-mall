package com.taotao.service.impl;


import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *内容分类管理服务层
 * @author : yechaoze
 * @date : 2019/5/3 21:09
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper mapper;

    /**
     *获取节点列表
     * @author : yechaoze
     * @date : 2019/5/27 13:04
     * @param parentId :
     * @return : java.util.List<com.taotao.common.pojo.EasyUITreeNode>
     */
    @Override
    public List<EasyUITreeNode> getContentCatList(Long parentId) {
        //根据parentId查询子节点列表
        TbContentCategoryExample example=new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        //设置查询条件
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> categoryList = mapper.selectByExample(example);
        //创建EasyUITreeNode列表
        List<EasyUITreeNode> nodeList=new ArrayList<>();
        for (TbContentCategory tbContentCategory:categoryList){
            EasyUITreeNode node=new EasyUITreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            nodeList.add(node);
        }
        return nodeList;
    }

    /**
     *新增节点
     * @author : yechaoze
     * @date : 2019/5/27 13:04
     * @param parentId :
     * @param name :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult addContentCategory(Long parentId, String name) {
        //创建表对应的pojo
        TbContentCategory category=new TbContentCategory();
        //设置属性
        category.setName(name);
        category.setCreated(new Date());
        category.setUpdated(new Date());
        category.setParentId(parentId);
        //默认为1
        category.setSortOrder(1);
        //1:正常 2:删除
        category.setStatus(1);
        //新插入的节点一定是叶子节点
        category.setIsParent(false);
        //插入数据库
        mapper.insert(category);
        //判断父节点的isparent属性,不是true改为true
        //根据parentId查询父节点
        TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
        if (!parent.getIsParent()){
            parent.setIsParent(true);
            //更新到数据库中
            mapper.updateByPrimaryKey(parent);
        }
        return TaoTaoResult.ok(category);
    }

    /**
     * 重命名节点
     * @author : yechaoze
     * @date : 2019/5/27 13:03
     * @param id :
     * @param name :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult renameContentCategory(Long id, String name) {
        //获取当前id的对象
        TbContentCategory category = mapper.selectByPrimaryKey(id);
        //设置修改名字及更新的日期
        category.setName(name);
        category.setUpdated(new Date());
        //更新数据库
        mapper.updateByPrimaryKey(category);
        return TaoTaoResult.ok();
    }

    /**
     *删除父节点及下面所有子节点的做法
     * @author : yechaoze
     * @date : 2019/5/4 18:15
     * @param id :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @Override
    public TaoTaoResult deleteContentCategory(Long id) {
        TbContentCategory category = mapper.selectByPrimaryKey(id);
        if (category.getIsParent()){
            List<EasyUITreeNode> list=getContentCatList(id);
            //递归删除
            for (EasyUITreeNode node : list) {
                deleteContentCategory(node.getId());
            }
        }
        if (getContentCatList(category.getParentId()).size() == 1) {
            TbContentCategory parentCategory = mapper.selectByPrimaryKey(category.getParentId());
            parentCategory.setIsParent(false);
            mapper.updateByPrimaryKey(parentCategory);
        }
        mapper.deleteByPrimaryKey(id);
        return TaoTaoResult.ok();
    }

    /**
     *不删除节点的做法
    * @author : yechaoze
    * @date : 2019/5/4 18:34
     * @param id :
    * @return : com.taotao.common.utils.TaoTaoResult
     */
//    @Override
//    public TaoTaoResult deleteContentCategory(Long id) {
//        //获取id值与id相同的对象
//        TbContentCategory category = mapper.selectByPrimaryKey(id);
//        Long parentId=category.getParentId();
//        //判断要删除的节点是否为父节点
//        if (category.getIsParent()){
//                return TaoTaoResult.build(1,"父节点无法删除");
//        }else {
//            //删除
//            mapper.deleteByPrimaryKey(id);
//            //查询parentId相符的对象
//            TbContentCategoryExample example=new TbContentCategoryExample();
//            TbContentCategoryExample.Criteria criteria = example.createCriteria();
//            criteria.andParentIdEqualTo(parentId);
//            List<TbContentCategory> list = mapper.selectByExample(example);
//            //判断是否为空节点
//            if (list.size()==0){
//                TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
//                //设置父节点的is_parentId值为false
//                if (parent.getIsParent()) {
//                    parent.setIsParent(false);
//                    mapper.updateByPrimaryKey(parent);
//                }
//            }
//        }
//        return TaoTaoResult.ok(category);
//    }
}
