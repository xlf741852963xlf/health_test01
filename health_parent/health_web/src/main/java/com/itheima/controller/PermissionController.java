package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.pojo.User;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;
    @PostMapping("/findPermision")
    public Result findPermision(@RequestBody QueryPageBean queryPageBean){
        // 调用业务服务分页查询
        PageResult<Permission> pageResult = permissionService.findPage(queryPageBean);
        return new Result(true, MessageConstant.GET_PERMISSION_SUCCESS, pageResult);
    }
    @PostMapping("/deletePermission")
    public Result deletePermission(Integer id){
        permissionService.deletePermission(id);
        return new Result(true,MessageConstant.DELETE_PERMISSION_SUCCESS);
    }
    @PostMapping("/addPermission")
    public Result addPermission(@RequestBody Permission permission){
        permissionService.addPermission(permission);
        return new Result(true,MessageConstant.ADD_PERMISSION_SUCCESS);
    }
    @PostMapping("/updatePermission")
    public Result updatePermission(@RequestBody Permission permission){
        permissionService.updatePermission(permission);
        return new Result(true,MessageConstant.EDIT_PERMISSION_SUCCESS);
    }
    @GetMapping("/findPermissionById")
    public Result findPermissionById(Integer id){
        Permission permission=permissionService.findPermissionById(id);
        return new Result(true,MessageConstant.GET_PERMISSION_SUCCESS,permission);
    }
}
