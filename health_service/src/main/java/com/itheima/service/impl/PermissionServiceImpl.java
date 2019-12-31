package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.PermissionDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.Permission;
import com.itheima.service.PermissionService;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {
	@Autowired
	private PermissionDao permissionDao;
	@Autowired
	private SqlSessionFactory sqlSessionFactory;
	/**
	 * 添加权限
	 */
	@Override
	public void addPermission(Permission permission, Integer[] roleIds) {
		//调用dao添加权限
		// 添加检查组
		permissionDao.addPermission(permission);
		// 获取检查组的id
		Integer permissionId = permission.getId();
		// 遍历checkitemIds，
		if(null != roleIds){
			// 有选择检查项
			for (Integer roleId : roleIds) {
				//添加中间表关系
				permissionDao.addPermissionRole(roleId, permissionId);
			}
		}
	}

	/**
	 * 删除权限
	 * @param id
	 */
	@Override
	public void delete(Integer id) {
		//清除中间表中的关系
		permissionDao.deleteAssociationWithRole(id);
		//调用dao通过id删除权限
		permissionDao.deletePermission(id);
	}

	/**
	 * 获得权限列表数据
	 * @return
	 */
	@Override
	public PageResult<Permission> getPermissions(QueryPageBean queryPageBean) {
		// 判断是否有条件
		if(!StringUtils.isEmpty(queryPageBean.getQueryString())){
			// 有查询条件，补%,模糊查询
			queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
		}
		// 使用分页插件
		PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
		// 查询会被分页
		Page<Permission> page = permissionDao.findByCondition(queryPageBean.getQueryString());
		// 封装分页结果
		PageResult<Permission> pageResult = new PageResult<Permission>(page.getTotal(), page.getResult());
		return pageResult;
	}

	/**
	 * 通过id
	 * @param id
	 */
	@Override
	public Permission findById(Integer id) {
		//通过id查询权限
		return permissionDao.findById(id);
	}

	/**
	 * 修改权限
	 * @param permission
	 */
	@Override
	public void update(Permission permission, Integer[] roleIds) {
		permissionDao.update(permission);
		// 删除旧的关系
		permissionDao.deleteAssociationWithRole(permission.getId());
		// 遍历添加新的关系
		if (null != roleIds) {
			SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
			PermissionDao PermissionDaoMapper = sqlSession.getMapper(PermissionDao.class);
			for (Integer roleId : roleIds) {
				// 此时就是使用了批量添加
				permissionDao.addPermissionRole(roleId, permission.getId());
			}
			// 提交session
			sqlSession.commit();
			sqlSession.flushStatements();
			sqlSession.close();
		}
	}
}
