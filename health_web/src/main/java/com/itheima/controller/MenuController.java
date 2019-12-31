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
}
