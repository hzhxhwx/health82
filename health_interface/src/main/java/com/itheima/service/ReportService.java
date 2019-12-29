package com.itheima.service;

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
}
