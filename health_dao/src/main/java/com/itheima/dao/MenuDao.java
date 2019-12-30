package com.itheima.dao;
/*
 *@author FuCanliang
 */

import com.itheima.pojo.Menu;

import java.util.List;

public interface MenuDao {
    List<Menu> getMenuByUsername(String username);
}
