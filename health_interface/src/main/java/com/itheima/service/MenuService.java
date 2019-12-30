package com.itheima.service;
/*
 *@author FuCanliang
 */

import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenu(String username);
}
