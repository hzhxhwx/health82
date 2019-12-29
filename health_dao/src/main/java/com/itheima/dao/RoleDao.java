package com.itheima.dao;

import com.itheima.pojo.Role;

import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface RoleDao {
    /**
     * 通过用户id查询用户所拥有的角色集合
     * @param userId
     * @return
     */
    Set<Role> findByUserId(Integer userId);
}
