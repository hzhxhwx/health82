package com.itheima.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
public class ServiceApplication {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:application-service.xml");
        new Scanner(System.in).nextLine();
    }
}
