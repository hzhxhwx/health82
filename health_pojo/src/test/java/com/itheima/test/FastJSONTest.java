package com.itheima.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Member;
import org.junit.Test;

import java.util.Date;

/**
 * @user: Eric
 * @date: 2019/12/27
 * @description:
 */
public class FastJSONTest {

    @Test
    public void testWriteMapNullValue(){
        CheckItem checkItem = new CheckItem();
        checkItem.setId(1);
        checkItem.setCode("abcd");
        System.out.println(JSON.toJSONString(checkItem));
        System.out.println(JSON.toJSONString(checkItem, SerializerFeature.WriteMapNullValue));
    }

    @Test
    public void testWriteDateUseDateFormat(){
        Member member = new Member();
        member.setRegTime(new Date());
        member.setName("zhangsan");
        System.out.println(JSON.toJSONString(member));
        System.out.println(JSON.toJSONString(member, SerializerFeature.WriteDateUseDateFormat));
    }
}
