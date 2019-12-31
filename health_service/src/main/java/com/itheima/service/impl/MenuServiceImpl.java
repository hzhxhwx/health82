package com.itheima.service.impl;
/*
 *@author FuCanliang
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;


    /**
     * 根据用户名获取当前用户的权限和菜单，实现方法一
     *
     * @param username
     * @return List<Menu>
     */
    @Override
    public List<Menu> getMenu(String username) {

        //创建List<Menu> userMenus存放用户拥有的父子菜单
        ArrayList<Menu> userMenus = new ArrayList<>();

        //根据用户名获取用户权限下的所有菜单（包括父菜单和子菜单）
        List<Menu> menuList = menuDao.getMenuByUsername(username);

        for (Menu menu : menuList) {
            //组装父菜单和子菜单
            Integer parentMenuId = menu.getParentMenuId();
            if (null == parentMenuId) {
                //没有上级菜单，说明这就是一级菜单了,new一个children集合存放这个父菜单中的所有子菜单,子菜单也在menuList中
                ArrayList<Menu> children = new ArrayList<>();

                //获取父菜单的path，与子菜单path中的第一有效字符比较
                String parentMenuPath = menu.getPath();
                for (Menu  childrenMenu : menuList) {
                    String pathStr = childrenMenu.getPath();
                    //截取菜单的path中的第一个有效字符，以此判断父菜单
                    try {
                        String pathParentNumber = pathStr.split("-")[0].substring(1);
                        if (pathParentNumber.equals(parentMenuPath)){
                            //将子菜单加入父菜单
                            children.add(childrenMenu);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }
                menu.setChildren(children);
                userMenus.add(menu);
            }
        }
        return userMenus;
    }

    /**
     * 根据用户名获取当前用户的权限和菜单，实现方法二
     *
     * @param username
     * @return
     */
    @Override
    public List<Menu> getMenu2(String username) {

        //创建List<Menu> userMenus存放用户拥有的父子菜单,最终的返回值
        ArrayList<Menu> level1MenuList = new ArrayList<>();

        //创建临时变量map，用于中转所有Menu
        HashMap<Integer, Menu> map = new HashMap<>();

        //根据用户名获取用户权限下的所有菜单（包括父菜单和子菜单）
        List<Menu> menuList = menuDao.getMenuByUsername(username);

        //遍历所有菜单，转换格式为map<parentMenuId,childernMenu>
        for (Menu menu : menuList) {
            Integer parentMenuId = menu.getParentMenuId();
            map.put(menu.getId(),menu);
            if(null==parentMenuId){
                //一级菜单，存入返回的对象
                level1MenuList.add(menu);
            }
        }


        for (Menu menu : menuList) {
            Integer parentMenuId = menu.getParentMenuId();
            //1.跳过父菜单
            if(null==parentMenuId){
                continue;
            }
            //2.处理子菜单
            //2.1获取子菜单的父菜单对象
            Menu parentMenu = map.get(parentMenuId);
            //2.2获取父菜单中的子菜单集合，追加子菜单
            parentMenu.getChildren().add(menu);
        }
        return level1MenuList;
    }

    /**
     * 菜单分页查询
     * @param queryPageBean
     * @return
     */
    @Override
    public Result findPage(QueryPageBean queryPageBean) {
        if (!StringUtil.isEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> page = menuDao.findPage(queryPageBean.getQueryString());
        PageResult<Menu> result = new PageResult<Menu>(page.getTotal(),page.getResult());
        return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,result);

    }

    /**
     * 添加菜单
     * @param roleIds
     * @param menu
     */
    @Override
    public void add(Integer[] roleIds, Menu menu) {
        //添加菜单
        HashMap<String, Object> map = new HashMap<>();
        map.put("name",menu.getTitle());
        map.put("linkUrl",menu.getLinkUrl());
        map.put("path",menu.getPath());
        map.put("priority",menu.getPriority());
        map.put("description",menu.getDescription());
        map.put("icon",menu.getIcon());
        map.put("parentMenuId",menu.getParentMenuId());
        menuDao.add(map);
        //建立关系
        addMenuAndRole(roleIds,menu);
    }

    public void addMenuAndRole(Integer[] roleIds, Menu menu){
        Integer id = menu.getId();
        Map<String, Integer> map = new HashMap<>();
        if (id!=null){
            for (Integer roleId : roleIds) {
                //添加关系表
                map.put("menu_id", id);
                map.put("role_id", roleId);

            }
            menuDao.addMenuAndRole(map);
        }

    }
}
