package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/27
 * @description:
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 运营统计数据
     * @return
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        // 今天
        String today = DateUtils.date2StringYMD(new Date());
        // 本周星期一
        String monday = DateUtils.date2StringYMD(DateUtils.getThisWeekMonday());
        // 本周星期天
        String sunday = DateUtils.date2StringYMD(DateUtils.getSundayOfThisWeek());
        // 本月1号
        String firstDayOfThisMonth = DateUtils.date2StringYMD(DateUtils.getFirstDayOfThisMonth());
        // 本月最后一天
        String lastDayOfThisMonth = DateUtils.date2StringYMD(DateUtils.getLastDayOfThisMonth());
        // 本日新增会员数
        int todayNewMember = memberDao.findMemberCountByDate(today);
        // 总会员数
        int totalMember = memberDao.findMemberTotalCount();
        // 本周新增会员数
        int thisWeekNewMember = memberDao.findMemberCountAfterDate(monday);
        // 本月新增会员数
        int thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDayOfThisMonth);
        // 本日预约数
        int todayOrderNumber = orderDao.findOrderCountByDate(today);
        // 本日到诊数
        int todayVisitsNumber = orderDao.findVisitsCountByDate(today);
        // 本周预约数
        int thisWeekOrderNumber = orderDao.findOrderCountBetweenDate(monday, sunday);
        // 本周到诊数
        int thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(monday);
        // 本月预约数
        int thisMonthOrderNumber = orderDao.findOrderCountBetweenDate(firstDayOfThisMonth, lastDayOfThisMonth);
        // 本月到诊数
        int thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDayOfThisMonth);
        // 热门套餐
        List<Map<String,Object>> hotPackage = orderDao.findHotPackage();
        Map<String, Object> resultMap = new HashMap<String,Object>();
        resultMap.put("reportDate",today);
        resultMap.put("todayNewMember",todayNewMember);
        resultMap.put("totalMember",totalMember);
        resultMap.put("thisWeekNewMember",thisWeekNewMember);
        resultMap.put("thisMonthNewMember",thisMonthNewMember);
        resultMap.put("todayOrderNumber",todayOrderNumber);
        resultMap.put("todayVisitsNumber",todayVisitsNumber);
        resultMap.put("thisWeekOrderNumber",thisWeekOrderNumber);
        resultMap.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        resultMap.put("thisMonthOrderNumber",thisMonthOrderNumber);
        resultMap.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        resultMap.put("hotPackage",hotPackage);
        return resultMap;
    }
}
