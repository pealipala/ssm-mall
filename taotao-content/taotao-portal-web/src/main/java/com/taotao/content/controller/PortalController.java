package com.taotao.content.controller;

import com.taotao.content.service.PortalContentService;
import com.taotao.pojo.TbContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 首页广告内容controller
 * @author : yechaoze
 * @date : 2019/5/27 14:29
 */
@Controller
public class PortalController {

    @Autowired
    private PortalContentService service;

    @Value("${CONTENT_DISPLAY_ID}")
    private Long CONTENT_DISPLAY_ID;

    /**
     * 展示广告
     * @author : yechaoze
     * @date : 2019/5/27 14:29
     * @param model :
     * @return : java.lang.String
     */
    @RequestMapping("/index")
    public String showIndex(Model model){
        List<TbContent> ad1List = service.getContentListById(CONTENT_DISPLAY_ID);
        model.addAttribute("ad1List",ad1List);
        return  "index";
    }
}
