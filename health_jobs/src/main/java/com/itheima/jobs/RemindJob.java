package com.itheima.jobs;

import com.aliyuncs.exceptions.ClientException;
import com.itheima.dao.OrderDao;
import com.itheima.utils.DateUtils;
import com.itheima.utils.SMSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.List;

@Component
public class RemindJob {

    @Autowired
    private OrderDao orderDao;

    public void remindJobTelephone(){
        //获取当前日期对象
        Calendar cal = Calendar.getInstance();
        //获取明天的日期
        cal.add(Calendar.DATE,+1 );
        //转化日期
        String date = DateUtils.date2StringYMD(cal.getTime());
        //查询会员预约日期为明天的电话号码
        List<String> telephones=orderDao.queryMemberTelephone(date);
        //发送短信提示会员明天记得体检
        for (String telephone : telephones) {
            //System.out.println(telephone);
            try {
                SMSUtils.sendShortMessage(telephone,SMSUtils.ORDER_NOTICE , "123456");
            } catch (ClientException e) {
                e.printStackTrace();
            }
        }

    }
}
