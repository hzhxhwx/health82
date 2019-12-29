package com.itheima.jobs;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
@Component
public class JobDemo {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 执行任务,方法名可以随便, 将来要与配置中的方法名一致
     */
    public void doJob(){
        System.out.println(sdf.format(new Date()) + ": Hello World!");
    }
}
