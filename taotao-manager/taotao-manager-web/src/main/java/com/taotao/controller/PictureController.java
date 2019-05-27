package com.taotao.controller;

import com.taotao.common.utils.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 上传图片controller
 * @author : yechaoze
 * @date : 2019/5/27 14:20
 */
@Controller
public class PictureController {

    @Value("${IMAGE_SERVER_URL}")
    private String IMAGE_SERVER_URL;

    /**
     * 图片上传
     * @author : yechaoze
     * @date : 2019/5/27 14:21
     * @param uploadFile :
     * @return : java.util.Map
     */
    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map uploadFile(MultipartFile uploadFile){
        try {
            //把图片上传到图片服务器
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/client.conf");
            //获取文件拓展名
            String filename = uploadFile.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            //得到图片地址和文件名
            String url=fastDFSClient.uploadFile(uploadFile.getBytes(),extName);
            url=IMAGE_SERVER_URL+url;
            Map result=new HashMap();
            result.put("error",0);
            result.put("url",url);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            Map result=new HashMap();
            result.put("error",1);
            result.put("message","上传失败");
            return result;
        }
    }


}

