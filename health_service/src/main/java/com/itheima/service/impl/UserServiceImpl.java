package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    /**
     * 查询登陆用户信息，登陆验证
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if(null != user){
            // 填充用户所拥有的角色
            Set<Role> userRoles = roleDao.findByUserId(user.getId());
            if(null != userRoles) {
                user.setRoles(userRoles);
                // 角色所拥有的权限
                for (Role role : userRoles) {
                    Set<Permission> permissions = permissionDao.findByRoleId(role.getId());
                    // 填充到角色中
                    role.setPermissions(permissions);
                }
            }
        }
        return user;
    }

    @Override
    public User findByUsername2(String username) {
        return userDao.findUserRolePermissionByUsername(username);
    }
}
