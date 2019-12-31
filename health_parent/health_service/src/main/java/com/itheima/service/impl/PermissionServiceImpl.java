package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = PermissionService.class)
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;
    /**获取返回权限信息*/
    @Override
    public PageResult<Permission> findPage(QueryPageBean queryPageBean) {
        String queryString = queryPageBean.getQueryString();
        if (queryString!=null && queryString.length()!=0){
            queryPageBean.setQueryString("%"+queryString+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Permission> page=permissionDao.findPermission(queryPageBean.getQueryString());
        PageResult<Permission> pageResult=new PageResult<>(page.getTotal(),page.getResult());
        return pageResult;
    }

    /**删除指定的权限*/

    @Override
    public void deletePermission(Integer id) throws MyException {
        //查找要删除的权限是否被角色引用
        Integer count=permissionDao.findPermissionInMid(id);
        if (count>0){
            throw new MyException("无法失败,该权限已经被角色引用");
        }
        permissionDao.deletePermission(id);
    }

    /**增加权限数据*/
    @Override
    public void addPermission(Permission permission) {
        permissionDao.addPermission(permission);
    }

    /**编辑权限数据*/
    @Override
    public void updatePermission(Permission permission) {
        permissionDao.updatePermission(permission);
    }

    /**根据id获取权限数据*/
    @Override
    public Permission findPermissionById(Integer id) {
        return permissionDao.findPermissionById(id);
    }
}
