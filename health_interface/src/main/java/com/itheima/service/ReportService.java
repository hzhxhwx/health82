package com.itheima.service;

import com.itheima.entity.Result;

import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/27
 * @description:
 */
public interface ReportService {
    /**
     * 运营统计数据
     * @return
     */
    Map<String,Object> getBusinessReportData();

    /**
     * 根据选择时间段获取会员折线图
     * @param beginDate
     * @param endDate
     * @return
     */
    Result getMemberReportBySelection(String beginDate, String endDate) throws Exception;
}
