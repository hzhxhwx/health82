package com.itheima.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@Service
public class SpringSecurityUserService implements UserDetailsService {

    /**
     * 一定是在服务上的，它属于另一台电脑
     */
    @Reference
    private UserService userService;

    /**
     * 用户的认证与授权
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名查询数据库,此时这个用户，包含roles, roles.permission都有值
        User user = userService.findByUsername2(username);
        if(null == user){
            return null;// 报AccessDeniedException 用户没权限
        }
        // 授权, 用户的权限集合
        List<GrantedAuthority> authorityList = new ArrayList<GrantedAuthority>();
        // 角色授权
        Set<Role> userRoles = user.getRoles();
        if (null != userRoles) {
            SimpleGrantedAuthority sga = null;
            for (Role role : userRoles) {
                // 角色授权
                sga = new SimpleGrantedAuthority(role.getKeyword());
                authorityList.add(sga);
                // 角色下还有权限，授予权限
                Set<Permission> rolePermissions = role.getPermissions();
                if(null != rolePermissions){
                    // 授予权限
                    for (Permission permission : rolePermissions) {
                        sga = new SimpleGrantedAuthority(permission.getKeyword());
                        authorityList.add(sga);
                    }
                }
            }
        }
        // 返回登陆用户的认证信息与权限信息
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorityList);
    }
}
