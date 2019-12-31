package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.User;

import java.util.List;
import java.util.Map;

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

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    List<Integer> findRoleIdsByUserId(Integer userId);

    /**
     * 添加用户
     * @param user
     */
    void add(User user);

    /**
     * 添加用户和角色关系
     * @param map
     */
    void addUserAndRole(Map map);

    /**
     * 更新用户
     * @param user
     */
    void update(User user);

    /**
     * 删除用户和角色的关系
     * @param id
     */
    void deleteUserAndRole(Integer id);

    /**
     * 删除用户
     * @param userId
     */
    void deleteUser(Integer userId);
}
