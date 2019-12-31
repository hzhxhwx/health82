package com.itheima.dao;

import com.github.pagehelper.Page;
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

    /**
     * 分页查询
     * @return
     */
    Page<Role> findPage(String queryString);

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    Role findById(Integer id);

    /**
     * 编辑角色
     */
    void update(Role role);

    /**
     * 根据角色ID查询是否被user引用
     * @param id
     * @return
     */
    Integer findAssociatinById(Integer id);

    void deleteAssociationByRoleIdFromt_role_permission(Integer id);
    void deleteAssociationByRoleIdFromt_role_menu(Integer id);
    void deleteAssociationByRoleIdFromt_user_role(Integer id);
    void deleteById(Integer id);

    void add(Role role);


}
