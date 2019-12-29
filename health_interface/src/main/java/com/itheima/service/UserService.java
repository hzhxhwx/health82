package com.itheima.service;

import com.itheima.pojo.User;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface UserService {
    /**
     * 查询登陆用户信息
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 查询登陆用户信息
     * @param username
     * @return
     */
    User findByUsername2(String username);
}
