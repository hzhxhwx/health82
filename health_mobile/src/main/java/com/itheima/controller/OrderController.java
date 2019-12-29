package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Order;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    /**
     * 提交 订单
     * @param paramMap
     * @return
     */
    @PostMapping("/submit")
    public Result submit(@RequestBody Map<String,String> paramMap){
        // 验证验证码
        Jedis jedis = jedisPool.getResource();
        String telephone = paramMap.get("telephone");
        String key = telephone + "_" + RedisMessageConstant.SENDTYPE_ORDER;
        String codeInRedis = jedis.get(key);
        if(null == codeInRedis){
            // 用户没有点击获取、超时了
            return new Result(false, MessageConstant.VALIDATECODE_FETCH);
        }
        // 有值，配置验证码
        jedis.del(key);// 验证码不能重复使用
        if(!codeInRedis.equalsIgnoreCase(paramMap.get("validateCode"))){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        // 调用业务服务
        // 设置订单的类型
        paramMap.put("orderType", Order.ORDERTYPE_WEIXIN);
        Result result = orderService.submit(paramMap);
        return result;
    }

    /**
     * 查询预约信息
     * @param id
     * @return
     */
    @GetMapping("/findById")
    public Result findById(int id){
        // 调用业务查询
        Map<String,Object> resultMap = orderService.findById(id);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS, resultMap);
    }
}
