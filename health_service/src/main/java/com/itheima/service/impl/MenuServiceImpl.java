package com.itheima.service.impl;
/*
 *@author FuCanliang
 */

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MenuDao;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service(interfaceClass = MenuService.class)
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;


    /**
     * 根据用户名获取当前用户的权限和菜单
     *
     * @param username
     * @return
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
}
