package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        // security的配置信息 session
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取 认证信息
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication.getName());
        // 主角，主事人=》登陆用户
        User principal = (User) authentication.getPrincipal();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, principal.getUsername());
    }

    @PostMapping("/findUser")
    public Result findUser(@RequestBody QueryPageBean queryPageBean){
        // 调用业务服务分页查询
        PageResult<com.itheima.pojo.User> pageResult = userService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_USER_SUCCESS, pageResult);
    }

    @PostMapping("/deleteUser")
    public Result deleteUser(Integer id){
        //删除管理用户
        userService.deleteById(id);
        return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
    }

    @GetMapping("/findAllRoles")
    public Result findAllRoles(){
        try {
            List<Role> roles=userService.findAllRoles();
            return new Result(true,MessageConstant.GET_ROLE_SUCCESS,roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ROLE_FAIL);
        }

    }
    @PostMapping("/addUser")
    public Result addUser(@RequestBody com.itheima.pojo.User user, Integer[] ids){
        userService.addUser(user,ids);
        return new Result(true,MessageConstant.ADD_USER_SUCCESS);
    }

    @GetMapping("/findUserById")
    public Result findUserById(Integer id){
        com.itheima.pojo.User user =userService.findUserById(id);
        return new Result(true,MessageConstant.GET_USER_SUCCESS,user);
    }
    @GetMapping("/findAllRolesById")
    public Result findAllRolesById(){
        List<Role> roles=userService.findAllRolesById();
        return new Result(true,MessageConstant.GET_USER_SUCCESS,roles);
    }
    @GetMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer id){
        List<Integer> ids=userService.findRoleIdsByUserId(id);
        return new Result(true,MessageConstant.GET_USER_SUCCESS,ids);
    }
    @PostMapping("/updateUser")
    public Result updateUser(@RequestBody com.itheima.pojo.User user, Integer[] roleIds){
        userService.updateUser(user,roleIds);
        return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
    }


    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.forEach(l->{
            l = l + "123";
        });
        System.out.println(list);
    }

}
