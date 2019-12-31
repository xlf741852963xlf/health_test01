package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface RoleService {
    PageResult<Role> findPage(QueryPageBean queryPageBean);

    List<Permission> findAllPermission();

    List<Menu> findAllMenus();

    void addRole(Role role, Integer[] menuIds, Integer[] permissionIds);

    Role findRoleById(Integer id);

    Integer[] findPermissionIdsByRoleId(Integer roleId);

    Integer[] findMenuIdsByRoleId(Integer roleId);

    void deleteRole(Integer id) throws MyException;

    void updateRole(Role role, Integer[] menuIds, Integer[] permissionIds);
}
