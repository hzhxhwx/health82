package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.CheckItem;

import java.util.List;

/**
 * @user: Eric
 * @date: 2019/12/16
 * @description:
 */
public interface CheckItemDao {

    /**
     * 添加
     * @param checkItem
     */
    void add(CheckItem checkItem);

    /**
     * 分页条件查询
     * @param queryString
     * @return
     */
    Page<CheckItem> findByCondition(String queryString);

    /**
     * 通过编号查询
     * @param id
     * @return
     */
    CheckItem findById(int id);

    /**
     * 更新
     * @param checkItem
     */
    void update(CheckItem checkItem);

    /**
     * 检查项是否被引用
     * @param id
     * @return
     */
    int countByCheckItemId(int id);

    /**
     * 删除检查项
     * @param id
     */
    void deleteById(int id);

    /**
     * 查询所有检查项
     * @return
     */
    List<CheckItem> findAll();
}
