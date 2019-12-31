package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 查询登陆用户信息，登陆验证
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(null != user){
            // 填充用户所拥有的角色
            Set<Role> userRoles = roleDao.findByUserId(user.getId());
            if(null != userRoles) {
                user.setRoles(userRoles);
                // 角色所拥有的权限
                for (Role role : userRoles) {
                    Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
                    // 填充到角色中
                    role.setPermissions(permissions);
                }
            }
        }
        return user;
    }

    @Override
    public User findByUsername2(String username) {
        return userDao.findUserRolePermissionByUsername(username);
    }


    @Override
    public PageResult<User> findPage(QueryPageBean queryPageBean) {
        // 判断是否有条件
        if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
            // 有查询条件，补%,模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // 使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 查询会被分页
        Page<User> page = userDao.findByCondition(queryPageBean.getQueryString());
        // 封装分页结果
        PageResult<User> pageResult = new PageResult<User>(page.getTotal(), page.getResult());
        return pageResult;
    }
/**根据id删除管理用户*/
    @Override
    @Transactional
    public void deleteById(Integer id) {
        //先删除用户表与角色表之间的关系
        userDao.DeleteUserInMid(id);
        //删除用户表中指定id的user
        userDao.DeleteUserInUser(id);
    }
    /**查询返回所有角色数据信息*/
    @Override
    public List<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    /**增加管理用户*/
    @Override
    @Transactional
    public void addUser(User user, Integer[] ids) {
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.addUser(user);
        if (ids!=null && ids.length!=0){
            for (Integer id : ids) {
                userDao.addUserRoleIds(user.getId(),id);
            }
        }
    }

    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    @Override
    public List<Role> findAllRolesById() {
        return userDao.findAllRolesById();
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        return userDao.findRoleIdsByUserId(id);
    }

    @Override
    @Transactional
    public void updateUser(User user, Integer[] roleIds) {
        userDao.deleteRoleIdsByUserId(user.getId());
        if (roleIds!=null && roleIds.length!=0){
            for (Integer roleId : roleIds) {
                userDao.insertRoleIdsByUserId(user.getId(),roleId);
            }
        }
        userDao.updateUser(user);
    }
}
