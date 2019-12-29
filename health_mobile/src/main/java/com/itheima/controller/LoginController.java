package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/24
 * @description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private MemberService memberService;

    /**
     * 快速登陆
     * @param paramMap
     * @return
     */
    @PostMapping("/check")
    public Result loginCheck(@RequestBody Map<String,String> paramMap, HttpServletResponse res){
        // 验证验证码
        Jedis jedis = jedisPool.getResource();
        String telephone = paramMap.get("telephone");
        String key = telephone + "_" + RedisMessageConstant.SENDTYPE_LOGIN;
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
        // 判断是否为会员
        Member member = memberService.findByTelephone(telephone);
        if(null == member){
            // 不存在
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberService.add(member);
        }

        // 跟踪用户的行为Cookie
        Cookie cookie = new Cookie("login_member_telephone", telephone);
        // 存活时间
        cookie.setMaxAge(60*60*24*30);// 1个月
        // 访问网站时带上cookie
        cookie.setPath("/");
        res.addCookie(cookie);
        // 返回结果给前端
        return new Result(true, MessageConstant.LOGIN_SUCCESS);
    }
}
