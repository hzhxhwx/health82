package com.itheima.dao;

import com.itheima.pojo.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderDao {
    /**
     * 添加订单
     * @param order
     */
    void add(Order order);

    /**
     * 订单查询
     * @param order
     * @return
     */
    List<Order> findByCondition(Order order);

    /**
     * 订单详情
     * @param id
     * @return
     */
    Map findById4Detail(Integer id);
    Integer findOrderCountByDate(String date);
    Integer findOrderCountAfterDate(String date);
    Integer findVisitsCountByDate(String date);
    Integer findVisitsCountAfterDate(String date);
    List<Map<String,Object>> findHotPackage();

    Integer findOrderCountBetweenDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
