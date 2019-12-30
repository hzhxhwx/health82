package com.itheima.dao;

import com.github.pagehelper.Page;
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

    /**
     * 用户分页查询
     * @param queryString
     * @return
     */
    Page<User> findPage(String queryString);

    /**
     * 通过id查询user
     * @param id
     * @return
     */
    User findById(Integer id);
}
