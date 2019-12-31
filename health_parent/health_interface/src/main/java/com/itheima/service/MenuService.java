package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService {
    /**根据id得到对应的菜单项*/
    Menu findById(Integer id);

    PageResult<Menu> findMenuPage(QueryPageBean queryPageBean);

    void deleteMenu(Integer id);

    List<Map<String, Object>> findFirstMenu();

    Integer getFirstMenuNum();

    Integer getSecondMenuNum(Integer id);

    void addMenu(Menu menu);

    Integer getPriority(Integer integer);

    Menu findMenuById(Integer id);

    void updateMenu(Menu menu);
}
