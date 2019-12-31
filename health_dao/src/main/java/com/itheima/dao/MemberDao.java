package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MemberDao {
   List<Member> findAll();
   Page<Member> selectByCondition(String queryString);
   void add(Member member);
   void deleteById(Integer id);
   Member findById(Integer id);
   Member findByTelephone(String telephone);
   void edit(Member member);

   Integer findMemberCountBeforeDate(String date);
   Integer findMemberCountByDate(String date);
   Integer findMemberCountAfterDate(String date);
   Integer findMemberTotalCount();

   /**
    * 根据起始日期查询会员数
    * @param beginDate
    * @param endDate
    * @return
    */
   Integer findMemberCountBetween(@Param("beginDate") String beginDate, @Param("endDate") String endDate);

   /**
    * 根据会员性别分类, 获取会员数量
    * @return
    */
   List<Map<String,Object>> getMemberCountBySex();

   /**
    * 根据会员年龄段分类, 获取会员数量
    * @return
    */
   List<Map<String,Object>> getMemberCountByAge(
           @Param("today") String date1,
           @Param("eighteenYearsBefore") String date2,
           @Param("thirtyYearsBefore") String date3,
           @Param("fortyFiveYearsBefore") String date4);
}
