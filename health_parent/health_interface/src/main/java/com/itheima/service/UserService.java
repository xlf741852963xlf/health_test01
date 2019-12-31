package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface UserService {
    /**
     * 查询登陆用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询登陆用户信息
     * @param username
     * @return
     */
    User findByUsername2(String username);

    PageResult<User> findPage(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    List<Role> findAllRoles();

    void addUser(User user, Integer[] ids);

    User findUserById(Integer id);

    List<Role> findAllRolesById();

    List<Integer> findRoleIdsByUserId(Integer id);

    void updateUser(User user, Integer[] roleIds);

}
