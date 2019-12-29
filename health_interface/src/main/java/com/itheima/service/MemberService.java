package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
public interface MemberService {
    /**
     * 通过手机号码查询会员信息
     * @param telephone
     * @return
     */
    Member findByTelephone(String telephone);

    /**
     * 添加会员
     * @param member
     */
    void add(Member member);

    /**
     * 获取会员拆线图数据
     * @return
     */
    Map<String,List<Object>> getMemberReport();
}
