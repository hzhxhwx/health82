package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.RoleDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author hzh
 * @Date 2019/12/30 19:59
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @Override
    public PageResult<Role> findPage(QueryPageBean queryPageBean) {
        //判断是否有模糊查询条件，有的话给条件加上%
        String queryString = queryPageBean.getQueryString();
        if (queryString != null && !"".equals(queryString)) {
            queryPageBean.setQueryString("%" + queryString + "%");
        }
        //使用分页插件，开启分页，紧接着的下一句查询就会实现分页查询
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //使用分页工具提供的Page对象，但是此对象字段使用的是基本数据类型，而不是包装数据类型，不能实现序列化和反序列化，无法传输
        Page<Role> pages = roleDao.findPage(queryPageBean.getQueryString());
        //将Controller需要的内容单独抽离封装到PageResult
        PageResult<Role> pageResult = new PageResult<>(pages.getTotal(), pages.getResult());
        return pageResult;
    }


    /**
     * 根据ID查询角色信息
     *
     * @param id
     * @return
     */
    @Override
    public Role findById(Integer id) {
        Role role = roleDao.findById(id);
        return role;
    }

    /**
     * 根据ID删除角色
     *
     * @param id
     */
    @Override
    @Transactional
    public void delete(Integer id) {
        //删除角色需要维护三张表t_role_permission、t_role_menu、t_user_role
        roleDao.deleteAssociationByRoleIdFromt_role_permission(id);
        roleDao.deleteAssociationByRoleIdFromt_role_menu(id);
        roleDao.deleteAssociationByRoleIdFromt_user_role(id);
        roleDao.deleteById(id);
    }

    /**
     * 编辑角色
     *
     * @param role
     */
    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }
}
