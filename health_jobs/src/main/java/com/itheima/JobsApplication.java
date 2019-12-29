package com.itheima;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
public class JobsApplication {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:applicationContext-jobs.xml");
    }
}
