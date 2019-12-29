package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.QiNiuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
@Component
public class RemoveImgFromQiNiuJob {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 清除垃圾图片
     */
    public void doJob(){
        Jedis jedis = jedisPool.getResource();
        // 计算redis中所有图片与保存到数据库的图片的差集,sdiff
        // 这个差集就是我们需要删除的图片
        Set<String> need2Delete = jedis.sdiff(RedisConstant.PACKAGE_PIC_RESOURCES, RedisConstant.PACKAGE_PIC_DB_RESOURCES);
        // 把需要删除的文件名转成数组
        String[] need2DeletePicNames = need2Delete.toArray(new String[]{});
        // 调用7牛删除
        QiNiuUtils.removeFiles(need2DeletePicNames);
        // 删除这个key，让redis更精简，不影响业务
        jedis.del(RedisConstant.PACKAGE_PIC_RESOURCES, RedisConstant.PACKAGE_PIC_DB_RESOURCES);
    }
}
