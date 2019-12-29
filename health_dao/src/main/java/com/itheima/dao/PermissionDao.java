package com.itheima.dao;

import com.itheima.pojo.Permission;

import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface PermissionDao {
    /**
     * 通过角色roleId查询角色下所拥有的权限
     * @param roleId
     * @return
     */
    Set<Permission> findByRoleId(Integer roleId);
}
