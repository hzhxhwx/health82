package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
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

	/**
	 * 添加权限
	 * @param permission
	 */
	void addPermission(Permission permission);

	/**
	 * 删除权限
	 * @param id
	 */
	void deletePermission(Integer id);

	/**
	 * 获得权限列表数据
	 * @return
	 */
	List<Permission> getPermissions();

	/**
	 * 通过id查询权限
	 * @param id
	 * @return
	 */
	Permission findById(Integer id);

	/**
	 * 修改权限
	 * @param permission
	 */
	void update(Permission permission);

	/**
	 * 通过权限id删除角色权限中间表中的联系
	 * @param id
	 */
	void deleteAssociationWithRole(Integer id);

	/**
	 * 条件查询
	 * @param queryString
	 * @return
	 */
	Page<Permission> findByCondition(String queryString);

	/**
	 * 添加权限角色中间表数据
	 * @param permissionId
	 * @param roleId
	 */
	void addPermissionRole(@Param("roleId") Integer roleId, @Param("permissionId") Integer permissionId);
}
