package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/20
 * @description:
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    /**
     * 批量导入
     * @param list
     */
    @Override
    @Transactional
    public void doImport(List<OrderSetting> list) {
        //有数据才处理
        if(null != list && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                // 判断 通过预约日期来判断是否存在设置信息
                OrderSetting osInDb = orderSettingDao.findByOrderData(orderSetting.getOrderDate());
                // 存在
                if(null != osInDb){
                    // 如果数量不一致，才需要覆盖
                    if(osInDb.getNumber() != orderSetting.getNumber()){
                        // 更新数量
                        orderSettingDao.editNumberByOrderDate(orderSetting);
                    }
                }else {
                    // 不存在
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    /**
     * 展示月份预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth(String month) {
        // 拼接开始日期
        String startDate = month + "-01";
        // 拼接结束日期
        String endDate = month + "-31";
        // 调用Dao实现日期范围查询 List<OrderSetting>
        List<OrderSetting> list = orderSettingDao.getOrderSettingBetweenDate(startDate, endDate);
        // 转成List<Map<String,Integer> [{ date: 4, number: 120, reservations: 120 }]
        // 数据集合
        List<Map<String,Integer>> dataList = new ArrayList<Map<String,Integer>>();
        // 每一天的数据
        Map<String,Integer> data = null;
        if(null != list && list.size() > 0){
            for (OrderSetting os : list) {
                // 构建符合前端的数据格式{ date: 4, number: 120, reservations: 120 }
                data = new HashMap<String,Integer>();
                data.put("date", os.getOrderDate().getDate());
                data.put("number",os.getNumber());
                data.put("reservations", os.getReservations());
                dataList.add(data);
            }
        }
        return dataList;
    }

    /**
     * 展示月份预约设置信息
     * @param month
     * @return
     */
    @Override
    public List<Map<String, Integer>> getOrderSettingByMonth2(String month) {
        // 拼接开始日期
        String startDate = month + "-01";
        // 拼接结束日期
        String endDate = month + "-31";
        // 调用Dao实现日期范围查询 List<OrderSetting>
        List<Map<String,Integer>> dataList = orderSettingDao.getOrderSettingBetweenDate2(startDate, endDate);
        return dataList;
    }

    @Override
    public List<OrderSetting> getOrderSettingByMonth3(String month) {
        // 拼接开始日期
        String startDate = month + "-01";
        // 拼接结束日期
        String endDate = month + "-31";
        // 调用Dao实现日期范围查询 List<OrderSetting>
        List<OrderSetting> list = orderSettingDao.getOrderSettingBetweenDate(startDate, endDate);
        return list;
    }

    /**
     * 通过日期修改可预约数量
     * @param orderSetting
     */
    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        // 日期一定是2019-11-22。看你的数据库中的日期格式
        // 判断 通过预约日期来判断是否存在设置信息
        OrderSetting osInDb = orderSettingDao.findByOrderData(orderSetting.getOrderDate());
        // 存在
        if(null != osInDb){
            // 如果数量不一致，才需要覆盖
            if(osInDb.getNumber() != orderSetting.getNumber()){
                // 更新数量
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }
        }else {
            // 不存在
            orderSettingDao.add(orderSetting);
        }
    }
}
