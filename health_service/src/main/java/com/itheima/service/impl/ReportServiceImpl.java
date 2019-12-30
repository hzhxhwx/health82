package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

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
    /**
     * 根据起始时间段查询会员数量
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public Result getMemberReportBySelection(String beginDate, String endDate) throws Exception {
        //
        //创建一个months集合存储月份
        List<Object> months = new ArrayList<Object>();
        //创建一个集合用于存储会员数量
        List<Object> memberCount = new ArrayList<Object>();
        Calendar cal = Calendar.getInstance();
        //起始和结束日期的年月日 这里使用getTime将日期转成毫秒值有问题 用了死方法
        int beginMonth = -1;
        int beginYear = -1;
        int beginDay = -1;
        int endMonth = -1;
        int endYear = -1;
        int endDay = -1;
        Date firstSelectionDate = null;
        Date lastSelectionDate = null;
        String firstSelection = null;
        String lastSelection = null;
        //将查询的起始日期转成yyyy-MM的date类型
        firstSelectionDate = DateUtils.parseString2Date(beginDate, "yyyy-MM-dd");
        lastSelectionDate = DateUtils.parseString2Date(endDate, "yyyy-MM-dd");
        //起始和 结束日期String 类型
        firstSelection = DateUtils.parseDate2String(firstSelectionDate, "yyyy-MM");
        lastSelection = DateUtils.parseDate2String(lastSelectionDate, "yyyy-MM");
        cal.setTime(DateUtils.parseString2Date(beginDate));
        //获得起始时期月份
        beginMonth = cal.get(Calendar.MONTH) + 1;
        beginYear = cal.get(Calendar.YEAR);
        beginDay = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(DateUtils.parseString2Date(endDate));
        //获得结束日期月份
        endMonth = cal.get(Calendar.MONTH) + 1;
        endYear = cal.get(Calendar.YEAR);
        endDay = cal.get(Calendar.DAY_OF_MONTH);
        //1 判断起始日期是否比结束日期晚  如果是 抛出异常
        //这种方法有问题 暂时不用
//        int flag = (int) (firstSelectionDate.getTime() - lastSelectionDate.getTime());
        //年份差
        int val1 = endYear - beginYear;
        //月份差
        int val2 = endMonth - beginMonth;
        //日期差
        int val3 = endDay - beginDay;
        if(val1 < 0){
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL, "选择年份区间错误，请重新选择");
        }
        if(val1 == 0 && val2 < 0) {
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL, "选择月份区间错误，请重新选择");
        }
        if(val1 == 0 && val2 >= 0 && val3 < 0){
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL, "选择日期区间错误，请重新选择");
        }
        //2 将开始日期的月份作为起始索引下标 结束日期月份作为结束索引
        for (int i = beginMonth; i <= endMonth; i++) {
            //3 将遍历的索引(月份)添加到 集合中
            months.add(i);
        }
        //3 设置Calendar的日期
        cal.setTime(firstSelectionDate);
        //获得日期的月份
        int month = cal.get(Calendar.MONTH);
        String mon = null;
        //4 遍历集合 到数据库中查询每月份的会员数量
        int index = 0;
        for (Object m : months) {
            mon = DateUtils.parseDate2String(cal.getTime(), "yyyy-MM");
            cal.add(Calendar.MONTH, 1);
            //判断遍历的月份是否是选中的起始日期的月份和结束日期的月份
            //若果是被选中的日期 带上当天的日期yyyy-MM-dd
            if(index == 0){
                months.set(index, beginDate);
            }else if(index == months.size() - 1){
                months.set(index, endDate);
            }else{
                months.set(index, mon);
            }
            //如果是 则使用选中的起始日期 和 起始日期月份的月底日期作为条件
            if(mon.equals(firstSelection)){
                //第一个月的数据由起始时间截止到本月最后一天进行查询
                memberCount.add(memberDao.findMemberCountBetween(beginDate, mon + "-31"));
            }else{
                //判断当前月是否是最后一个月
                if(mon.equals(lastSelection)){
                    //是最后一个月查询条件为结束日期endDate
                    memberCount.add(memberDao.findMemberCountBeforeDate(endDate));
                }else{
                    //不是最后一个月 直接将yyyy-MM的日期后拼接-31 作为条件查询
                    memberCount.add(memberDao.findMemberCountBeforeDate(mon + "-31"));
                }
            }
            index ++;
        }
        //创建Map集合存储数据
        Map<String,List<Object>> dataMap = new HashMap<String,List<Object>>();
        dataMap.put("months", months);
        dataMap.put("memberCount", memberCount);
        //返回数据
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, dataMap);
    }

}
