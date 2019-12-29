package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.PackageService;
import com.itheima.pojo.Package;
import com.itheima.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/21
 * @description:
 */
@RestController
@RequestMapping("/package")
public class PackageController {
    
    @Reference
    private PackageService packageService;

    /**
     * 套餐表数据查询
     * @return
     */
    @GetMapping("/getPackage")
    public Result getPackage(){
        List<Package> list = packageService.findAll();
        // 拼接图片地址
        list.forEach(e->{
            e.setImg(QiNiuUtils.DOMAIN + e.getImg());
        });
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, list);
    }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(int id){
        // 调用业务查询
        Package pkg = packageService.findDetailById(id);
        // 拼接图片地址
        pkg.setImg(QiNiuUtils.DOMAIN + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    @GetMapping("/findDetailById2")
    public Result findDetailById2(int id){
        // 调用业务查询
        Package pkg = packageService.findDetailById2(id);
        // 拼接图片地址
        pkg.setImg(QiNiuUtils.DOMAIN + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,pkg);
    }

    /**
     * 只有套餐信息，没有详细
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        Package pkg = packageService.findById(id);
        pkg.setImg(QiNiuUtils.DOMAIN + pkg.getImg());
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,pkg );
    }
}
