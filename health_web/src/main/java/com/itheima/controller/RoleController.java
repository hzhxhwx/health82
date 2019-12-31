package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Role;
import com.itheima.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.handler.MessageContext;
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


    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findpage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用service
        PageResult<Role> pageResult = roleService.findPage(queryPageBean);

       return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,pageResult);
    }

    /**
     * 根据ID查询角色信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(Integer id){
        Role role=roleService.findById(id);
        return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,role);
    }

    /**
     *编辑角色
     * @param role
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody Role role){
        roleService.update(role);
        return new Result(true,MessageConstant.EDIT_USER_SUCCESS);
    }

    /**
     * 删除角色
     * @return
     */
    @PostMapping("/delete")
    public Result delete(Integer id){
        try {
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (Exception e) {
            return new Result(false,MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    @PostMapping("/add")
    public Result add(@RequestBody Role role){
        roleService.add(role);
        return new Result(true,MessageConstant.ADD_ROLE_SUCCESS);
    }
}
