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

    /**
     * 更新菜单
     * @param menu
     * @param roleIds
     */
    void update(Menu menu, Integer[] roleIds);

    /**
     * 根据菜单id查询角色id
     * @param menuId
     * @return
     */
    List<Integer> findRoleIdsByMenuId(Integer menuId);
}
