package com.itheima.dao;
/*
 *@author FuCanliang
 */

import com.github.pagehelper.Page;
import com.itheima.pojo.Menu;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    List<Menu> getMenuByUsername(String username);

    /**
     * 菜单分页查询
     * @param queryString
     * @return
     */
    Page<Menu> findPage(String queryString);

    /**
     * 菜单和角色的关系
     * @param map
     */
    void addMenuAndRole(Map<String, Integer> map);

    /**
     * 添加菜单
     * @param menu
     */
    void add(Map map);
}
