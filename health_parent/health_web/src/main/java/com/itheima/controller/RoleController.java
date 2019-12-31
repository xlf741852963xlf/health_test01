package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;
    @PostMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult<Role> roles=roleService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_ROLE_SUCCESS,roles);
    }
    @GetMapping("/findAllPermission")
    public Result findAllPermission(){
        List<Permission> permissions=roleService.findAllPermission();
        return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,permissions);
    }
    @GetMapping("/findAllMenus")
    public Result findAllMenus(){
        List<Map<String,Object>> list_hm=new ArrayList();
        List<Menu> menus=roleService.findAllMenus();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
        if (menus!=null && menus.size()!=0){
            for (Menu menu : menus) {
                Map<String,Object> hm = new HashMap();
                hm.put("id",menu.getId());
                hm.put("label",menu.getName());
                List<Map<String,Object>> lists=new ArrayList<>();
                for (Menu child : menu.getChildren()) {
                    Map<String,Object> hms = new HashMap();
                    hms.put("id",child.getId());
                    System.out.println(child.getId());
                    hms.put("label",child.getName());
                    System.out.println(child.getName());
                    lists.add(hms);
                }
                hm.put("children",lists);
                list_hm.add(hm);
            }
        }
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS,list_hm);
    }
    @PostMapping("/addRole")
    public Result addRole(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds){
        roleService.addRole(role,menuIds,permissionIds);
        return new Result(true,MessageConstant.ADD_ROLES_SUCCESS);
    }

    @GetMapping("/findRoleById")
    public Result findRoleById(Integer id){
        Role role=roleService.findRoleById(id);
        return new Result(true,MessageConstant.GET_ROLE_SUCCESS,role);
    }
    @GetMapping("/findPermissionIdsByRoleId")
    public Result findPermissionIdsByRoleId(Integer roleId){
        Integer[] permissionIds=roleService.findPermissionIdsByRoleId(roleId);
        return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,permissionIds);
    }
    @GetMapping("/findMenuIdsByRoleId")
    public Result findMenuIdsByRoleId(Integer roleId){
        Integer[] menus=roleService.findMenuIdsByRoleId(roleId);
        for (Integer menu : menus) {
            System.out.println(menu);
        }
        return new Result(true,MessageConstant.GET_MENUS_SUCCESS,menus);
    }
    @PostMapping("/deleteRole")
    public Result deleteRole(Integer id){
        roleService.deleteRole(id);
        return new Result(true,MessageConstant.DELETE_ROLES_SUCCESS);
    }
    @PostMapping("/updateRole")
    public Result updateRole(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds){
        roleService.updateRole(role,menuIds,permissionIds);
        return new Result(true,MessageConstant.UPDATE_MENU_SUCCESS);
    }
}
