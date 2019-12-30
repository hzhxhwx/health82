package com.itheima.service;

import com.itheima.pojo.Role;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface RoleService {
    /**
     * 查询所有角色
     * @return
     */
    List<Role> findAll();
}
