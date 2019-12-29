package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/20
 * @description:
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 批量导入
     * @param excelFile
     * @return
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        // 解析excel
        try {
            // excel表中的所有行数据
            List<String[]> dataList = POIUtils.readExcel(excelFile);
            // 转成OrderSetting的集合
            List<OrderSetting> list = new ArrayList<OrderSetting>();
            if(null != dataList || dataList.size() > 0){
                SimpleDateFormat sdf = new SimpleDateFormat(POIUtils.DATE_FORMAT);
                OrderSetting os = null;
                // 遍历数组，每一个数组就是一个OrderSetting
                // rowDataStr[0]日期
                //rowDataStr[1] 数量
                for (String[] rowDataStr : dataList) {
                    os = new OrderSetting(sdf.parse(rowDataStr[0]), Integer.valueOf(rowDataStr[1]));
                    // 添加list集合中
                    list.add(os);
                }
                // 调用业务服务完成导入
                orderSettingService.doImport(list);
                return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
    }

    /**
     * 显示预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){
        List<Map<String,Integer>> list = orderSettingService.getOrderSettingByMonth2(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
    }

    /**
     * 显示预约设置信息
     * @param month
     * @return
     */
    @GetMapping("/getOrderSettingByMonth2")
    public Result getOrderSettingByMonth2(String month){
        List<OrderSetting> list = orderSettingService.getOrderSettingByMonth3(month);
        return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS, list);
    }

    @PostMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        orderSettingService.editNumberByDate(orderSetting);
        return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
    }
}
