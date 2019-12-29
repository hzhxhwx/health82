package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/20
 * @description:
 */
public interface OrderSettingService {

    /**
     * 批量导入预约设置
     * @param list
     */
    void doImport(List<OrderSetting> list);

    /**
     * 展示月份预约设置信息
     * @param month
     * @return
     */
    List<Map<String,Integer>> getOrderSettingByMonth(String month);

    List<Map<String, Integer>> getOrderSettingByMonth2(String month);

    /**
     * 展示月份预约设置信息
     * @param month
     * @return
     */
    List<OrderSetting> getOrderSettingByMonth3(String month);

    /**
     * 通过日期修改可预约数量
     * @param orderSetting
     */
    void editNumberByDate(OrderSetting orderSetting);
}
