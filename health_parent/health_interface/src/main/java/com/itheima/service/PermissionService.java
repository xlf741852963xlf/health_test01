package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.Permission;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface PermissionService {
    PageResult<Permission> findPage(QueryPageBean queryPageBean);

    void deletePermission(Integer id) throws MyException;

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    Permission findPermissionById(Integer id);
}
