package com.itheima.dao;

import com.itheima.pojo.User;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface UserDao {
    /**
     * 通过用户名查询用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 通过用户名查询用户信息及其所拥有的角色与权限
     */
    User findUserRolePermissionByUsername(String username);
}
