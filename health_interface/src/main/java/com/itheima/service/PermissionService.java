package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Permission;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
public interface PermissionService {
	/**
	 * 添加权限
	 * @param permission
	 */
	void addPermission(Permission permission, Integer[] roleIds);

	/**
	 * 通过id删除权限
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * 获取权限列表数据
	 * @return
	 */
	PageResult<Permission> getPermissions(QueryPageBean queryPageBean);

	/**
	 * 根据id查询权限
	 * @param id
	 */
	Permission findById(Integer id);

	/**
	 * 修改权限
	 * @param permission
	 */
	void update(Permission permission, Integer[] roleIds);

	/**
	 * 根据权限id查询角色id
	 * @param permissonId
	 * @return
	 */
    List<Integer> findRoleIdsByPermissionId(Integer permissonId);
}
