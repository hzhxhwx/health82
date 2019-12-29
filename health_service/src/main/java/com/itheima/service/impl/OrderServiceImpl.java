package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.dao.OrderSettingDao;
import com.itheima.entity.Result;
import com.itheima.exception.MyException;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/23
 * @description:
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    /**
     * 提交预约服务
     * @param paramMap
     * @return
     */
    @Override
    @Transactional
    public Result submit(Map<String, String> paramMap) {
        //- 判断是否可预约  通过预约日期(前端)查询，orderSettingDao.findByOrderDate
        String orderDateStr = paramMap.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date orderDate = sdf.parse(orderDateStr);
            OrderSetting orderSetting = orderSettingDao.findByOrderData(orderDate);
            //  不存在：报错不能预约
            if(null == orderSetting){
                throw new MyException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
            }
            //  存在：number>reservations 有空位，没空位就报错
            if(orderSetting.getReservations() >= orderSetting.getNumber()){
                // 满了
                throw new MyException(MessageConstant.ORDER_FULL);
            }
            //- 判断是否为会员 通过手机号(前端)查询 memberDao.findByTelephone
            String telephone = paramMap.get("telephone");
            Member member = memberDao.findByTelephone(telephone);
            //  - 会员存在：取出他的id
            if(null == member){
                member = new Member();
                //  - 不存在，添加会员 再取出id   memberDao.add  selectKey
                member.setIdCard(paramMap.get("idCard"));
                member.setName(paramMap.get("name"));
                member.setSex(paramMap.get("sex"));
                member.setRegTime(new Date());
                member.setPhoneNumber(telephone);
                memberDao.add(member);
            }
            Integer memberId = member.getId();
            //- 判断用户是否已经预约过这个套餐（t_order）
            //  -   member_id, package_id, orderDate  orderDao.findByCondition
            Order order = new Order();
            order.setMemberId(memberId);
            order.setPackageId(Integer.valueOf(paramMap.get("packageId")));
            order.setOrderDate(orderDate);
            List<Order> orderList = orderDao.findByCondition(order);
            //  - 存在就要报错，重复预约了
            if(null != orderList && orderList.size() > 0){
                throw new MyException(MessageConstant.HAS_ORDERED);
            }
            //- 插入订单表    orderDao.add() selectKey
            order.setOrderType(Order.ORDERTYPE_WEIXIN);
            order.setOrderStatus(Order.ORDERSTATUS_NO);
            orderDao.add(order);
            //- 更新已经预约的数量  orderSettingDao.updateReservations
            //orderSetting.setReservations(orderSetting.getReservations() + 1); 由sql语句来完成
            orderSettingDao.editReservationsByOrderDate(orderSetting);
            //- 返回订单对象给Controller
            return new Result(true, MessageConstant.ORDER_SUCCESS, order);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Result(false, MessageConstant.ORDER_FAIL);
    }

    /**
     * 查询预约信息
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> findById(int id) {
        return orderDao.findById4Detail(id);
    }
}
