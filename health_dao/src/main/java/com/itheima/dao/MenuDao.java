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
     * @param map
     */
    void add(Map map);



    /**
     * 删除中间表关系
     * @param id
     */
    void deleteAssociationWithRole(Integer id);

    /**
     * 添加中间表关系
     * @param roleId
     * @param id
     */
    void addMenuRole(Integer roleId, Integer id);

    /**
     *更新菜单
     * @param map
     */
    void update(Map map);

    /**
     * 根据菜单id查询角色id
     * @param menuId
     * @return
     */
    List<Integer> findRoleIdsByMenuId(Integer menuId);

    /**
     * 通过id删除菜单
     * @param id
     */
    void delete(Integer id);

    Menu findById(Integer id);
}
