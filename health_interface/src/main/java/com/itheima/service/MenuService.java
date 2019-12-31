package com.itheima.service;
/*
 *@author FuCanliang
 */

import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenu(String username);

    List<Menu> getMenu2(String username);

    /**
     * 菜单分页查询
     * @param queryPageBean
     * @return
     */
    Result findPage(QueryPageBean queryPageBean);

    /**
     * 添加菜单
     * @param roleIds
     * @param menu
     */
    void add(Integer[] roleIds, Menu menu);
}
