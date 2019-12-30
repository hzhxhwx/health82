package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
    @Autowired
    private JedisPool jedisPool;
    /**
     * 套餐表数据查询
     * @return
     */
    @GetMapping("/getPackage")
    public Result getPackage(){
        //获取缓存连接
        Jedis jedis = jedisPool.getResource();

        //获取packages
        String packages = jedis.get("packages");

        //判断packages
        if (packages == null || "".equals(packages)) {
            //调用service
            List<Package> list = packageService.findAll();

            // 拼接图片地址
            list.forEach(e -> {
                e.setImg(QiNiuUtils.DOMAIN + e.getImg());
            });

            //将list集合转换成json字符串格式
            String listJson = JSON.toJSONString(list);

            //存入redis
            jedis.set("packages",listJson);
            packages = jedis.get("packages");
        }
        //从缓存中读取数据
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS, packages);
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
        //获取缓存连接
        Jedis jedis = jedisPool.getResource();
        String redisPackage = jedis.get("package:"+id);
        //判断
        if (redisPackage == null ||"".equals(redisPackage)){
            // 调用业务查询
            Package pkg = packageService.findDetailById(id);
            // 拼接图片地址
            pkg.setImg(QiNiuUtils.DOMAIN + pkg.getImg());
            //将套餐数据存入redis
            String pkgJson = JSON.toJSONString(pkg);
            jedis.set("package:"+id,pkgJson);
            redisPackage = jedis.get("package:"+id);
        }
        return new Result(true, MessageConstant.QUERY_PACKAGE_SUCCESS,redisPackage);
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
