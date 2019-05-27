package com.taotao.retrieve.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理器
 * @author : yechaoze
 * @date : 2019/5/27 14:32
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    private static final Logger log= LoggerFactory.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        //控制台打印
        e.printStackTrace();
        //打印日志
        log.debug("测试输出的日志..........");
        log.info("系统发生异常了..........");
        log.error("系统发生异常", e);
        //发邮件、发短信
        //使用jmail工具包。发短信使用第三方的Webservice。
        //显示错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        return modelAndView;
    }
}
