package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;

import java.util.List;
import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface PermissionDao {
    /**
     * 通过角色roleId查询角色下所拥有的权限
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
    /**获取返回权限信息*/
    Page<Permission> findPermission(String queryString);

    Integer findPermissionInMid(Integer id);

    void deletePermission(Integer id);

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    Permission findPermissionById(Integer id);

    List<Permission> findAllPermission();

}
