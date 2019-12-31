package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过用户名查询用户信息及其所拥有的角色与权限
     */
    User findUserRolePermissionByUsername(String username);

    Page<User> findByCondition(String queryString);

    void DeleteUserInMid(Integer id);

    void DeleteUserInUser(Integer id);

    void addUser(User user);

    void addUserRoleIds(@Param("id") Integer id,@Param("ids")Integer ids);

    User findUserById(Integer id);

    List<Role> findAllRolesById();

    List<Integer> findRoleIdsByUserId(Integer id);

    void deleteRoleIdsByUserId(Integer id);

    void insertRoleIdsByUserId(@Param("id") Integer id,@Param("roleId") Integer roleId);

    void updateUser(User user);

}
