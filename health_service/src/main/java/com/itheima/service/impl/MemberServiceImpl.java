package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import sun.util.resources.cldr.en.CalendarData_en_MP;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 通过手机号码查询会员信息
     * @param telephone
     * @return
     */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
     * 添加会员
     * @param member
     */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }

    /**
     * 获取会员拆线图数据
     * @return
     */
    @Override
    public Map<String, List<Object>> getMemberReport() {
        // 获取过去一年的时间
        Calendar car = Calendar.getInstance();
        car.add(Calendar.YEAR,-1);
        // 循环12个月，
        List<Object> months = new ArrayList<Object>();
        List<Object> memberCount = new ArrayList<Object>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String month = null;
        for(int i = 0; i<12; i++){
            //每次递增一个月
            car.add(Calendar.MONTH,1);
            month = sdf.format(car.getTime());
            months.add(month);
            // 查询到这个月为止总会员数
            memberCount.add(memberDao.findMemberCountBeforeDate(month + "-31"));
        }
        Map<String,List<Object>> dataMap = new HashMap<String,List<Object>>();
        dataMap.put("months", months);
        dataMap.put("memberCount", memberCount);
        return dataMap;
    }

    public static void main(String[] args) {
        // 获取过去一年的时间
        Calendar car = Calendar.getInstance();
        car.add(Calendar.YEAR,-1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 循环12个月，
        for(int i = 0; i<12; i++){
            //每次递增一个月
            car.add(Calendar.MONTH,2);
            // 设置为下个月的1号
            car.set(Calendar.DATE,1);
            // 这个月的最后一天
            car.add(Calendar.DATE,-1);
            System.out.println(sdf.format(car.getTime()));
        }
    }
}
