package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import com.itheima.utils.QiNiuUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;
    @Autowired
    private JedisPool jedisPool;

    /**根据id得到对应的菜单项*/
    @GetMapping("/findById")
    public Result findById(Integer id){

        try {
            Menu menu=menuService.findById(id);
            return new Result(true,MessageConstant.GET_MENUBYID_SUCCESS,menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MENUBYID_FAIL);
        }
    }
    @PostMapping("/findMenuPage")
    public Result findMenuPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Menu> menus=menuService.findMenuPage(queryPageBean);
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS,menus);
    }
    @RequestMapping("/deleteMenu")
    public Result deleteMenu(Integer id){
        menuService.deleteMenu(id);
        return new Result(true,MessageConstant.DELETE_MENUS_SUCCESS);
    }
    @PostMapping("/findFirstMenu")
    public Result findFirstMenu(){
        System.out.println("进来查找了");
        List<Map<String,Object>> firstMenus=menuService.findFirstMenu();
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS,firstMenus);
    }
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        // 获取文件名 xxx.jpg
        String originalFilename = imgFile.getOriginalFilename();
        // 获得文件的后缀后名
        // 找出最后.的位置
        int index = originalFilename.lastIndexOf(".");
        // 文件后缀名 扩展名 有带.
        String icoName = originalFilename.substring(0,index+1);
        // 生成唯一的文件 uuid + 后缀名
        // 调用7牛上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), originalFilename);
            // 上传成功后，返回图片的地址(域名+唯一的名称),
            String imgageUrl = QiNiuUtils.DOMAIN + originalFilename;
            //{flag,message, data: 图片的地址},
            // 存入redis，哪个key 所有图片
            // sadd key value
            jedisPool.getResource().sadd(RedisConstant.MENU_PIC_RESOURCES, originalFilename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, imgageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }
    @PostMapping("/addMenu")
    public Result addMenu(@RequestBody Menu menu,Integer[] level){
        if (level==null && level.length==0){
            return new Result(false,MessageConstant.GET_MENUS_FAIL);
        }
        if (level.length==1){
            menu.setLevel(1);
            menu.setParentMenuId(null);
            Integer count=menuService.getFirstMenuNum();
            menu.setPriority(count+1);
            menu.setPath(count+2+"");
        }else {
            menu.setLevel(2);
            menu.setParentMenuId(level[1]);
            Integer count=menuService.getSecondMenuNum(level[1]);
            menu.setPriority(count+1);
            Integer priority=menuService.getPriority(level[1]);
            String str="/"+(priority+1)+"-"+(count+1);
            menu.setPath(str);
        }
        menuService.addMenu(menu);
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS);
    }
    @GetMapping("/findMenuById")
    public Result findMenuById(Integer id){
        Menu menu=menuService.findMenuById(id);
        String icon = menu.getIcon();
        menu.setIcon(QiNiuUtils.DOMAIN+icon);
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS,menu);
    }
    @PostMapping("/updateMenu")
    public Result updateMenu(@RequestBody Menu menu,Integer[] level){
        if (level==null && level.length==0){
            return new Result(false,MessageConstant.GET_MENUS_FAIL);
        }
        if (level.length==1){
            menu.setLevel(1);
            menu.setParentMenuId(null);
            Integer count=menuService.getFirstMenuNum();
            menu.setPriority(count+1);
            menu.setPath(count+2+"");
        }else {
            menu.setLevel(2);
            menu.setParentMenuId(level[1]);
            Integer count=menuService.getSecondMenuNum(level[1]);
            menu.setPriority(count+1);
            Integer priority=menuService.getPriority(level[1]);
            String str="/"+(priority+1)+"-"+(count+1);
            menu.setPath(str);
        }
        menuService.updateMenu(menu);
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS);
    }

}
