package com.itheima.dao;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/20
 * @description:
 */
public interface OrderSettingDao {
    /**
     * 通过预约日期查询预约设置信息
     * @param orderDate
     * @return
     */
    OrderSetting findByOrderData(Date orderDate);

    /**
     * 更新可预约数量
     * @param orderSetting
     */
    void editNumberByOrderDate(OrderSetting orderSetting);

    /**
     * 添加预约设置信息
     * @param orderSetting
     */
    void add(OrderSetting orderSetting);

    /**
     * 按日期范围查询预约设置信息
     * @param startDate
     * @param endDate
     * @return
     */
    List<OrderSetting> getOrderSettingBetweenDate(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 按日期范围查询预约设置信息
     * @param startDate
     * @param endDate
     * @return
     */
    List<Map<String,Integer>> getOrderSettingBetweenDate2(@Param("startDate") String startDate, @Param("endDate") String endDate);

    /**
     * 更新已预约人数
     */
    void editReservationsByOrderDate(OrderSetting orderSetting);
}
