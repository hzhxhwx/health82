package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单
 * @Author hzh
 * @Date 2019/12/31 10:00
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用service
        Result result = menuService.findPage(queryPageBean);
        return result;
    }
    @RequestMapping("/add")
    public Result add(Integer[] roleIds, @RequestBody Menu menu){
        menuService.add(roleIds,menu);
        return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
    }

    /**
     * 菜单更新
     * @param menu
     * @param roleIds
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody Menu menu, Integer[] roleIds){
        // 调用业务服务修改检查组
        menuService.update(menu, roleIds);
        return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
    }

    @RequestMapping("/findRoleIdsByMenuId")
    public Result findRoleIdsByMenuId(Integer menuId){
        List<Integer> RoleIds = menuService.findRoleIdsByMenuId(menuId);
        return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,RoleIds);
    }
    /**
     * 通过id删除菜单
     */
    @RequestMapping("/delete")
    public Result delete(Integer id){
        menuService.delete(id);
        return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id){
        Menu menu = menuService.findById(id);
        return new Result(true,MessageConstant.QUERY_MENU_SUCCESS,menu);
    }
}
