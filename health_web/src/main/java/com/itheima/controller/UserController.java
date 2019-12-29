package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/getLoginUsername")
    public Result getLoginUsername(){
        // security的配置信息 session
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取 认证信息
        Authentication authentication = context.getAuthentication();
        System.out.println(authentication.getName());
        // 主角，主事人=》登陆用户
        User principal = (User) authentication.getPrincipal();
        return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, principal.getUsername());
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbbb");
        list.add("cccc");
        list.forEach(l->{
            l = l + "123";
        });
        System.out.println(list);
    }
}
