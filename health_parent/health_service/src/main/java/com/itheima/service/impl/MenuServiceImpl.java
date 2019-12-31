package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    /**根据id得到对应的菜单项*/
    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public PageResult<Menu> findMenuPage(QueryPageBean queryPageBean) {
        if (!StringUtils.isEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> page=menuDao.findMenuPage(queryPageBean.getQueryString());
        return new PageResult<>(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteMenu(Integer id) {
        Integer count=menuDao.findMenuIdsFromt_role_menu(id);
        if (count>0){
            throw new MyException("无法删除,该菜单已经被角色引用");
        }
        menuDao.deleteMenu(id);
    }

    @Override
    public List<Map<String, Object>> findFirstMenu() {
        return menuDao.findFirstMenu();
    }

    @Override
    public Integer getFirstMenuNum() {
        return menuDao.getFirstMenuNum();
    }

    @Override
    public Integer getSecondMenuNum(Integer id) {
        return menuDao.getSecondMenuNum(id);
    }

    @Override
    public void addMenu(Menu menu) {
        menuDao.addMenu(menu);
    }

    @Override
    public Integer getPriority(Integer id) {
        return menuDao.getPriority(id);
    }

    @Override
    public Menu findMenuById(Integer id) {
        return menuDao.findMenuById(id);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuDao.updateMenu(menu);
    }
}
