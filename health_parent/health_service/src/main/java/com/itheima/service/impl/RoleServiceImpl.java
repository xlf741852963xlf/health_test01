package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import com.itheima.pojo.Menu;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service(interfaceClass = RoleService.class)
public class RoleServiceImpl implements RoleService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleDao roleDao;
    /**查询角色*/
    @Override
    public PageResult<Role> findPage(QueryPageBean queryPageBean) {
        if (queryPageBean.getQueryString()!=null && queryPageBean.getQueryString().length()!=0){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Role> page=roleDao.finRolesByQueryString(queryPageBean.getQueryString());
        return new PageResult<Role>(page.getTotal(),page.getResult());
    }
    /**查找所有的权限*/
    @Override
    public List<Permission> findAllPermission() {
        return permissionDao.findAllPermission();
    }

    /**获取所有的菜单*/
    @Override
    public List<Menu> findAllMenus() {
        return menuDao.findAllMenus();
    }

    /**增加角色*/
    @Override
    @Transactional
    public void addRole(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.addRole(role);
        roleDao.deleteMenuIds(role.getId());
        if (menuIds!=null && menuIds.length!=0){
            for (Integer menuId : menuIds) {
                roleDao.addMenuIds(role.getId(),menuId);
            }
        }
        roleDao.deletePermissionIds(role.getId());
        if (permissionIds!=null && permissionIds.length!=0){
            for (Integer permissionId : permissionIds) {
                roleDao.addPermissionIds(role.getId(),permissionId);
            }
        }
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleDao.findRoleById(id);
    }

    @Override
    public Integer[] findPermissionIdsByRoleId(Integer roleId) {
        return roleDao.findPermissionIdsByRoleId(roleId);
    }

    @Override
    public Integer[] findMenuIdsByRoleId(Integer roleId) {
        return roleDao.findMenuIdsByRoleId(roleId);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id) throws MyException {
        Integer count=roleDao.findRoleIdsFromt_Role_User(id);
        if (count>0){
            throw new MyException("无法删除,该角色已经被引用");
        }
        roleDao.deleteRoleIdsFromt_Role_Permission(id);
        roleDao.deleteRoleIdsFromt_Role_menu(id);
        roleDao.deleteRoleIdsFromt_Role(id);
    }

    @Override
    @Transactional
    public void updateRole(Role role, Integer[] menuIds, Integer[] permissionIds) {
        System.out.println(role.getId());
        roleDao.deleteMenuByRoleId(role.getId());
        if (menuIds!=null && menuIds.length!=0){
            for (Integer menuId : menuIds) {
                roleDao.addMenuIdsByRoleId(role.getId(),menuId);
            }
        }
        roleDao.deletePermissionIdsByRoleId(role.getId());
        if (permissionIds!=null && permissionIds.length!=0){
            for (Integer permissionId : permissionIds) {
                roleDao.addPermissionIdsByRoleId(role.getId(),permissionId);
            }
        }
        roleDao.updateRole(role);
    }
}
