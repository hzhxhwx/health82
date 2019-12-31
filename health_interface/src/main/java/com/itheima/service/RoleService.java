package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface RoleService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<Role> findPage(QueryPageBean queryPageBean);

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    Role findById(Integer id);

    /**
     * 编辑角色
     * @param role
     */
    void update(Role role);

    /**
     * 删除角色
     * @param id
     */
    void delete(Integer id);

    void add(Role role);
}
