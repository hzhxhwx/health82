package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.PackageService;
import com.itheima.service.ReportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jxls.common.Context;
import org.jxls.transform.poi.PoiContext;
import org.jxls.util.JxlsHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/26
 * @description:
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private PackageService packageService;

    @Reference
    private ReportService reportService;

    /**
     * 会员拆线图
     * @return
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport(){
        Map<String,List<Object>> resultData = memberService.getMemberReport();
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, resultData);
    }

    /**
     * 套餐占比数据
     * @return
     */
    @GetMapping("/getPackageReport")
    public Result getPackageReport(){
        // 调用业务服务获取套餐分组统计信息
        List<Map<String,Object>> packageCount = packageService.getPackageReport();
        // packageNames, packageCount
        // 组装套餐名称
        List<String> packageNames = new ArrayList<String>();
        for (Map<String, Object> pkgMap : packageCount) {
            // {value, name}
            packageNames.add(((String) pkgMap.get("name")));
        }
        // {packageNames, packageCount}
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("packageNames",packageNames);
        resultMap.put("packageCount",packageCount);
        return new Result(true, MessageConstant.GET_PACKAGE_COUNT_REPORT_SUCCESS, resultMap);
    }

    /**
     * 运营统计数据
     * @return
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        // 调用业务服务查询
        Map<String,Object> reportData = reportService.getBusinessReportData();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, reportData);
    }

    /**
     * 导出运营统计数据
     * @return
     */
    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest req, HttpServletResponse res){
        // 获取模板的路径 req.getSession().getServletContext().getRealPath webapp目录下
        String templatePath = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        // 创建工作薄
        try (
                Workbook wk = new XSSFWorkbook(templatePath);
             OutputStream os = res.getOutputStream();
        ){
            // 获取工作表
            Sheet sht = wk.getSheetAt(0);
            Map<String, Object> reportData = reportService.getBusinessReportData();
            // 填充数据
            sht.getRow(2).getCell(5).setCellValue(((String) reportData.get("reportDate")));

            sht.getRow(4).getCell(5).setCellValue((int)reportData.get("todayNewMember"));
            sht.getRow(4).getCell(7).setCellValue((int)reportData.get("totalMember"));
            sht.getRow(5).getCell(5).setCellValue((int)reportData.get("thisWeekNewMember"));
            sht.getRow(5).getCell(7).setCellValue((int)reportData.get("thisMonthNewMember"));

            sht.getRow(7).getCell(5).setCellValue((int)reportData.get("todayOrderNumber"));
            sht.getRow(7).getCell(7).setCellValue((int)reportData.get("todayVisitsNumber"));
            sht.getRow(8).getCell(5).setCellValue((int)reportData.get("thisWeekOrderNumber"));
            sht.getRow(8).getCell(7).setCellValue((int)reportData.get("thisWeekVisitsNumber"));
            sht.getRow(9).getCell(5).setCellValue((int)reportData.get("thisMonthOrderNumber"));
            sht.getRow(9).getCell(7).setCellValue((int)reportData.get("thisMonthVisitsNumber"));

            // 热门套餐
            List<Map<String,Object>> hotPackage = (List<Map<String,Object>>)reportData.get("hotPackage");
            int rowCnt = 12;
            Row row = null;
            for (Map<String, Object> pkg : hotPackage) {
                row = sht.getRow(rowCnt);
                row.getCell(4).setCellValue(((String) pkg.get("name")));
                row.getCell(5).setCellValue(((long) pkg.get("count")));
                BigDecimal proportion = (BigDecimal)pkg.get("proportion");
                row.getCell(6).setCellValue(proportion.doubleValue());
                row.getCell(7).setCellValue(((String) pkg.get("remark")));
                rowCnt++;
            }
            // 实现下载
            String filename = "运营统计数据.xlsx";
            filename = new String(filename.getBytes(),"ISO-8859-1");
            //   * 设置头信息，告诉浏览器，这是一个文件下载，内容体是文件
            res.setHeader("Content-Disposition","attachment;filename=" + filename);
            res.setContentType("application/vnd.ms-excel");
            wk.write(os);
            os.flush();
            //   * 工作簿写入到response的输出流中
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 导出运营统计数据
     * @return
     */
    @GetMapping("/exportBusinessReport2")
    public void exportBusinessReport2(HttpServletRequest req, HttpServletResponse res) {
        // 获取模板的路径 req.getSession().getServletContext().getRealPath webapp目录下
        String templatePath = req.getSession().getServletContext().getRealPath("/template/report_template2.xlsx");
        // 数据模型 MdelAndView model
        Context context = new PoiContext();
        context.putVar("obj", reportService.getBusinessReportData());
        try {
            res.setContentType("application/vnd.ms-excel");
            res.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            // 把数据模型中的数据填充到文件中
            JxlsHelper.getInstance().processTemplate(new FileInputStream(templatePath),res.getOutputStream(),context);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
