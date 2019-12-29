package com.itheima.controller;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/send4Order")
    public Result send4Order(String telephone){
        // 判断是否存在redis中
        Jedis jedis = jedisPool.getResource();
        String key = telephone + "_" + RedisMessageConstant.SENDTYPE_ORDER;
        String codeInRedis = jedis.get(key);
        if(null != codeInRedis){
            // 已经发送过了
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }
        // 生成验证码，再发送
        Integer validateCode = ValidateCodeUtils.generateValidateCode(6);
        // 发送
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone, validateCode + "");
            // 存入redis
            // key，seconds有效期
            jedis.setex(key, 60*5, validateCode + "");
            // 返回结果
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }

    /**
     * 快速登陆验证码
     * @param telephone
     * @return
     */
    @PostMapping("/send4Login")
    public Result send4Login(String telephone){
        Jedis jedis = jedisPool.getResource();
        // 判断是否已经发送过了
        String key= telephone + "_" + RedisMessageConstant.SENDTYPE_LOGIN;
        String codeInRedis = jedis.get(key);
        if(null != codeInRedis){
            return new Result(false, MessageConstant.SENT_VALIDATECODE);
        }
        // 没有就要生成验证码，发送，存入redis
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        try {
            SMSUtils.sendShortMessage(telephone, SMSUtils.VALIDATE_CODE, code + "");
            // 存入redis
            jedis.setex(key, 5*60, code+"");
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
    }
}
