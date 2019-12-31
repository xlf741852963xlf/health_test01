package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface RoleDao {
    /**
     * 通过用户id查询用户所拥有的角色集合
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);
    /**查询返回所有角色数据*/
    List<Role> findAllRoles();

    Page<Role> finRolesByQueryString(String queryString);

    void addRole(Role role);

    void addMenuIds(@Param("id") Integer id,@Param("menuId") Integer menuId);

    void deleteMenuIds(Integer id);

    void deletePermissionIds(Integer id);

    void addPermissionIds(@Param("id") Integer id,@Param("permissionid") Integer permissionId);

    Role findRoleById(Integer id);

    Integer[] findPermissionIdsByRoleId(Integer roleId);

    Integer[] findMenuIdsByRoleId(Integer roleId);

    Integer findRoleIdsFromt_Role_User(Integer id);

    void deleteRoleIdsFromt_Role_Permission(Integer id);

    void deleteRoleIdsFromt_Role_menu(Integer id);

    void deleteRoleIdsFromt_Role(Integer id);

    void deleteMenuByRoleId(Integer id);

    void addMenuIdsByRoleId(@Param("id") Integer id,@Param("menuId") Integer menuId);

    void deletePermissionIdsByRoleId(Integer id);

    void addPermissionIdsByRoleId(@Param("id") Integer id,@Param("permissionId") Integer permissionId);

    void updateRole(Role role);
}
