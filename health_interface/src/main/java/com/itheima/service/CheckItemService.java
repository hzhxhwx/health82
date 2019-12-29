package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.exception.MyException;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/16
 * @description:
 */
public interface CheckItemService {
    /**
     * 添加
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    /**
     * 通过编号查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 修改
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 删除
     * @param id
     */
    void deleteById(int id) throws MyException;

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
