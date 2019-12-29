package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Package;
import com.itheima.service.PackageService;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

/**
 * @user: Eric
 * @date: 2019/12/19
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
     * 上传图片
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("imgFile") MultipartFile imgFile){
        // 获取文件名 xxx.jpg
        String originalFilename = imgFile.getOriginalFilename();
        // 获得文件的后缀后名
        // 找出最后.的位置
        int index = originalFilename.lastIndexOf(".");
        // 文件后缀名 扩展名 有带.
        String extension = originalFilename.substring(index);
        // 生成唯一的文件 uuid + 后缀名
        String uniqueFilename = UUID.randomUUID() + extension;
        // 调用7牛上传
        try {
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), uniqueFilename);
            // 上传成功后，返回图片的地址(域名+唯一的名称),
            String imgageUrl = QiNiuUtils.DOMAIN + uniqueFilename;
            //{flag,message, data: 图片的地址},
            // 存入redis，哪个key 所有图片
            // sadd key value
            jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_RESOURCES, uniqueFilename);
            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, imgageUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
    }

    /**
     * 添加
     * @param pkg
     * @param checkgroupIds
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody Package pkg, Integer[] checkgroupIds){
        // 调用服务完成添加
        packageService.add(pkg, checkgroupIds);
        // 添加成功后，存储图片到redis中，哪个key 另一个key
        jedisPool.getResource().sadd(RedisConstant.PACKAGE_PIC_DB_RESOURCES, pkg.getImg());
        return new Result(true, MessageConstant.ADD_PACKAGE_SUCCESS);
    }

}
