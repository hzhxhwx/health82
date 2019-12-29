package com.itheima.service;

import com.itheima.pojo.Package;

import java.util.List;
import java.util.Map;

/**
 * @user: Eric
 * @date: 2019/12/19
 * @description:
 */
public interface PackageService {
    /**
     * 添加套餐
     * @param pkg
     * @param checkgroupIds
     */
    void add(Package pkg, Integer[] checkgroupIds);


    /**
     * 查询所有套餐
     * @return
     */
    List<Package> findAll();

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    Package findDetailById(int id);

    /**
     * 查询套餐详情
     * @param id
     * @return
     */
    Package findDetailById2(int id);

    /**
     * 查询套餐信息
     * @param id
     * @return
     */
    Package findById(int id);

    /**
     * 获取套餐分组统计信息
     * @return
     */
    List<Map<String,Object>> getPackageReport();
}
