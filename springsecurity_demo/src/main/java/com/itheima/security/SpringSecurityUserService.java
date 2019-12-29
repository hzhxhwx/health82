package com.itheima.security;

import com.itheima.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/24
 * @description:
 */
@Service
public class SpringSecurityUserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 通过用户名从数据库查询, 假设是从数据库查
        User user = findByUserName(username);
        //String username, 登陆用户名
        //String password, 数据库中的密码，如果是明文加补上{noop}
        //Collection<? extends GrantedAuthority> authorities 登陆用户的权限集合
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        // 此时，角色名要与security.xml配置 access="ROLE_ADMIN"要一致，否则没法访问
        SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(sga);
        // 授权
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, user.getPassword(),authorities);
        return userDetails;
    }

    /**
     * 模拟数据库查询
     * @param username
     * @return
     */
    private User findByUserName(String username){
        User user = new User();
        // 这里为加密过的，因此要去除{noop}
        user.setPassword("$2a$10$GrDUyTNelHhGSKZXKkA3yO9hbssAAVi/G8nOk4Eq9oJs9U370QWsG");
        user.setUsername("admin");
        return user;
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        // 密码加密
        System.out.println(encoder.encode("admin"));
        // 校验密码, 1：明文，2：密文
        System.out.println(encoder.matches("1234", "$2a$10$u/BcsUUqZNWUxdmDhbnoeeobJy6IBsL1Gn/S0dMxI2RbSgnMKJ.4a"));
        System.out.println(encoder.matches("1234", "$2a$10$3xW2nBjwBM3rx1LoYprVsemNri5bvxeOd/QfmO7UDFQhW2HRHLi.C"));
        System.out.println(encoder.matches("1234", "$2a$10$4CHoXjRLnBI7z0/C2Wh7Nea5ySidjr6LluOji0sTSBRnpxmmTSgJO"));
    }
}
