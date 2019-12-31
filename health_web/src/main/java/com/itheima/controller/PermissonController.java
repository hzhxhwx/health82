package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/permission")
public class PermissonController {
	@Reference
	private PermissionService permissionService;

	/**
	 * 添加权限
	 * @param permission
	 * @return
	 */
	@PostMapping("/add")
	public Result add(@RequestBody Permission permission, Integer[] roleIds){
		// 调用业务服务添加检查组
		permissionService.addPermission(permission, roleIds);
		return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
	}

	/**
	 * 删除权限
	 */
	@RequestMapping("/delete")
	public Result delPermission(Integer id){
		try {
			//调用service删除权限
			permissionService.delete(id);
			return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
		}
	}

	/**
	 * 查询权限
	 */
	@RequestMapping("/findPage")
	public Result getPermissions(@RequestBody QueryPageBean queryPageBean){
		try {
			//调用service查询权限列表数据
			PageResult<Permission> pageResult = permissionService.getPermissions(queryPageBean);
			return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, pageResult);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
		}
	}

	/**
	 * 通过id查询权限
	 */
	@RequestMapping("/findById")
	public Result findById(Integer id){
		try {
			//调用service根据id查询权限
			Permission permission = permissionService.findById(id);
			return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
		}
	}

	@RequestMapping("/update")
	public Result update(@RequestBody Permission permission, Integer[] roleIds){
		// 调用业务服务修改检查组
		permissionService.update(permission, roleIds);
		return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
	}
	@RequestMapping("/findRoleIdsByPermissionId")
	public Result findRoleIdsByPermissionId(Integer permissonId){
		List<Integer> list = permissionService.findRoleIdsByPermissionId(permissonId);
		return new Result(true,"查询成功",list);

	}
}
