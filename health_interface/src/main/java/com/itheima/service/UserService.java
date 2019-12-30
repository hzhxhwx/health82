package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
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

    /**
     * 用户分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<User> findPage(QueryPageBean queryPageBean);

    /**
     * 通过id 查询user
     * @param id
     * @return
     */
    User findById(Integer id);
}
