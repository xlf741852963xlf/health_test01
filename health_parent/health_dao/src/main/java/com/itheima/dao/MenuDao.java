package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuDao {

    Menu findById(Integer id);

    List<Menu> findAllMenus();

    Page<Menu> findMenuPage(String queryString);

    Integer findMenuIdsFromt_role_menu(Integer id);

    void deleteMenu(Integer id);

    List<Map<String, Object>> findFirstMenu();

    Integer getFirstMenuNum();

    Integer getSecondMenuNum(Integer id);

    void addMenu(Menu menu);

    Integer getPriority(Integer id);

    Menu findMenuById(Integer id);

    void updateMenu(Menu menu);
}
