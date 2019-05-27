package com.taotao.cart.controller;

import com.taotao.cart.service.CartService;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.JsonUtils;
import com.taotao.common.utils.TaoTaoResult;
import com.taotao.items.service.ItemService;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车controller
 * @author : yechaoze
 * @date : 2019/5/25 17:44
 */
@Controller
public class CartController {

    @Autowired
    private ItemService itemService;
    @Autowired
    private CartService cartService;

    /**
     *商品加入购物车
     * @author : yechaoze
     * @date : 2019/5/24 9:17
     * @param itemId :
     * @param num :
     * @param request :
     * @param response :
     * @return : java.lang.String
     */
    @RequestMapping("/cart/add/{itemId}")
    public String addCart(@PathVariable Long itemId,
                          @RequestParam(defaultValue = "1") Integer num,
                          HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser user= (TbUser) request.getAttribute("user");
        //登录状态将购物车写入redis
        if (user!=null){
            //保存到服务端
            cartService.addCart(user.getId(),itemId,num);
            //返回逻辑视图
            return "cartSuccess";
        }
        //未登录状态将购物车写入cookie
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartList(request);
        boolean isItem=false;
        //判断商品是否存在
        for (TbItem item :cartList) {
            if (item.getId()==itemId.longValue()){
                //存在则数量增加
                item.setNum(item.getNum()+num);
                isItem=true;
                break;
            }
        }
        if (!isItem){
            //不存在则根据商品id查询商品信息，得到TbItem对象
            TbItem tbItem = itemService.getItemById(itemId);
            String image = tbItem.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                tbItem.setImage(images[0]);
            }
            tbItem.setNum(num);
            //把商品添加对购物车列表
            cartList.add(tbItem);
        }
        //将购物车列表写入cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),259200,true);
        //返回添加成功页面
        return "cartSuccess";
    }

    /**
     *  从cookie中取购物车列表（封装使用）
     * @author : yechaoze
     * @date : 2019/5/24 8:10
     * @param request :
     * @return : java.util.List<com.taotao.pojo.TbItem>
     */
    public List<TbItem> getCartList(HttpServletRequest request){
        String json= CookieUtils.getCookieValue(request,"cart",true);
        //为空
        if (StringUtils.isBlank(json)){
            //返回一个空列表
            return new ArrayList<>();
        }
        //返回购物车列表
        return JsonUtils.jsonToList(json, TbItem.class);
    }

    /**
     * 购物车列表展示
     * @author : yechaoze
     * @date : 2019/5/24 9:17
     * @param request :
     * @return : java.lang.String
     */
    @RequestMapping("/cart/cart")
    public String showCart(HttpServletRequest request,HttpServletResponse response){
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartList(request);
        //判断用户是否为登录状态
        TbUser user = (TbUser) request.getAttribute("user");
        //登录状态
        if (user!=null){
            //将cookie购物车与redis购物车合并
            cartService.mergeCart(user.getId(),cartList);
            //删除cookie中购物车列表
            CookieUtils.deleteCookie(request,response,"cart");
            //从服务端取购物车列表
            cartList = cartService.getCartList(user.getId());
        }
        //未登录状态
        //将cookie中的购物车传递给页面
        request.setAttribute("cartList",cartList);
        //返回逻辑视图
        return "cart" ;
    }

    /**
     * 更新购物车商品数量
     * @author : yechaoze
     * @date : 2019/5/24 9:31
     * @param itemId :
     * @param num :
     * @param request :
     * @param response :
     * @return : com.taotao.common.utils.TaoTaoResult
     */
    @RequestMapping("/cart/update/num/{itemId}/{num}")
    @ResponseBody
    public TaoTaoResult  updateCartNum(@PathVariable Long itemId,@PathVariable Integer num,
                                       HttpServletRequest request,HttpServletResponse response){
        //判断用户是否登录
        TbUser user=(TbUser) request.getAttribute("user");
        //登录状态
        if (user!=null){
            cartService.updateCartList(user.getId(),itemId,num);
            return TaoTaoResult.ok();
        }
        //未登录状态
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartList(request);
        //遍历列表找到需要修改的商品
        for (TbItem item:cartList) {
            if (item.getId()==itemId.longValue()){
                //更新数量
                item.setNum(num);
                break;
            }
        }
        //将购物车写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),259200,true);
        //返回成功
        return TaoTaoResult.ok();
    }

    /**
     * 删除商品
     * @author : yechaoze
     * @date : 2019/5/25 11:15
     * @param itemId :
     * @return : java.lang.String
     */
    @RequestMapping("/cart/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,
                             HttpServletResponse response){
        //判断用户是否登录
        TbUser user=(TbUser) request.getAttribute("user");
        //用户登录状态
        if (user!=null){
            cartService.deleteCartList(user.getId(),itemId);
            return "redirect:/cart/cart.html";
        }
        //用户未登录状态
        //从cookie中取购物车列表
        List<TbItem> cartList = getCartList(request);
        //遍历购物车
        for (TbItem item:cartList) {
            //找到相同的商品
                if (item.getId()==itemId.longValue()){
                    //删除商品
                    cartList.remove(item);
                    break;
                }
            }
        //将列表写回cookie
        CookieUtils.setCookie(request,response,"cart",JsonUtils.objectToJson(cartList),259200,true);
        //重定向到购物车页面
        return "redirect:/cart/cart.html";
    }
}
