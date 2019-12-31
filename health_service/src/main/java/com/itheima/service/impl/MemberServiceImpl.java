package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.entity.Result;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.DateUtils;
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

    /**
     * 根据会员性别分类, 获取会员数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getMemberCountBySex() {
        return memberDao.getMemberCountBySex();
    }

    /**
     * 根据会员年龄段分类, 获取会员数量
     * @return
     */
    @Override
    public List<Map<String, Object>> getMemberCountByAge() throws Exception {
        String eighteenYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-18));
        String thirtyYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-30));
        String fortyFiveYearsBefore = DateUtils.parseDate2String(DateUtils.backwardCertainYears(-45));
        String today = DateUtils.parseDate2String(DateUtils.getToday());

        List<Map<String, Object>> list = memberDao.getMemberCountByAge(
                today,
                eighteenYearsBefore,
                thirtyYearsBefore,
                fortyFiveYearsBefore);
        //创建一个新的mapList
        List<Map<String,Object>> mapList = new ArrayList<>();

        for (Map<String, Object> map : list) {
            Map<String,Object> map2 = new HashMap<>();

            String ageCategory = (String) map.get("ageCategory");
            Long value = (Long) map.get("value");

            map2.put("name",ageCategory);
            map2.put("value",value);

            mapList.add(map2);

        }


        return mapList;
    }

}
