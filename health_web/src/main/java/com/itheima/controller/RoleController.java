package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 角色
 * @Author hzh
 * @Date 2019/12/30 19:53
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;

    @RequestMapping("/findAll")
    public Result findAll(){
        //调用service
       List<Role> list = roleService.findAll();
       return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,list);
    }
}
