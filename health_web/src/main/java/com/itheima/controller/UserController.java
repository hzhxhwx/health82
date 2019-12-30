package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import com.itheima.service.MenuService;
import com.itheima.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private MenuService menuService;

    @Reference
    private UserService userService;
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



    /**
     * 获取当前登陆的用户，调用框架api从框架提供的上下文容器中获取当前用户和权限以及菜单
     *
     * @return
     */
    @GetMapping("/getuser")
    public Result getUser() {
        try {
            HashMap<String, Object> map = new HashMap<>();
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<Menu> menuList = menuService.getMenu2(user.getUsername());

            map.put("user", user);
            map.put("menuList",menuList);
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, map);
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 用户分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean){
        //调用service
        PageResult<com.itheima.pojo.User> result = userService.findPage(queryPageBean);
        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,result);


    }

    /**
     * 根据id查询user
     * @param id
     * @return
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        //调用service
        com.itheima.pojo.User user = userService.findById(id);
        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,user);
    }

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    @RequestMapping("/findRoleIdsByUserId")
    public Result findRoleIdsByUserId(Integer userId){
        //调用service
        List<Integer> roleIds = userService.findRoleIdsByUserId(userId);
        return new Result(true,MessageConstant.QUERY_USER_SUCCESS,roleIds);
    }

}
